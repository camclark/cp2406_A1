import java.util.ArrayList;
import java.net.InetAddress;
import java.util.List;
import java.lang.reflect.*;



public class Server {
    private ArrayList<Player> playerList = new ArrayList<>();
    // eg of how to get from ArrayList -  server.playerList.get(0).getUsername()


    // the boolean that will be turned of to stop the server
    private boolean runSwitch;

    /*
     *  server constructor that receive the port to listen to for connection as parameter
     *  in console
     */
    private Server() throws Exception {
        InetAddress LOCAL_IP = InetAddress.getLocalHost();
    }


    public static void main(String[] args) throws Exception {
        int MY_PORT = 49152;
        // change later to as many as needed
        int playersRequired = 2;

        Server server = new Server();
        String[] splitMessage;

        // get connection of two players

        connector(MY_PORT, server, playersRequired);
    }

    private static void connector(int MY_PORT, Server server, int playersRequired) throws Exception {
        String[] splitMessage;
        Boolean serverFailed;
        Boolean startGame = false;

        while (!startGame) {
            String message = DirectUDP.receive(MY_PORT);
            splitMessage = message.split(" ");


            // show message - for testing
            System.out.println("Message split by space is:" );
            for (int i = 0; i < splitMessage.length; i++){
            System.out.print(splitMessage[i] + " ");
            }
            System.out.println();

            serverFailed = false;

            // add to list
            if (splitMessage[0].equals("ADD")) {
                String username  = splitMessage[1];
                Integer socket = Integer.parseInt(splitMessage[3]) ;

                for (int i = 0; i < server.playerList.size(); i++){
                    if (splitMessage[1].equals(server.playerList.get(i).getUsername())) {
                        serverFailed = true;
                    }
                }

                if (serverFailed){
                    message = "FAILED userNameTaken";
                    DirectUDP.send(socket, MY_PORT, "10.0.0.2", message);

                    // send a message to client saying that username is taken and to try again
                } else{
                    message = "OKAY";
                    server.playerList.add(new Player(username, socket));
                    DirectUDP.send(socket, MY_PORT, "10.0.0.2", message);
                    System.out.println("Added: " + username);
                }

                if (server.playerList.size() == playersRequired) {
                    startGame = true;
                    System.out.println("Two players connected: Game start");
                }
            }
        }
    }
}

