import java.util.ArrayList;
import java.net.InetAddress;


public class Server {
    private ArrayList<String> playerList = new ArrayList<>();
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
        int PORT = 49152;

        Server s1 = new Server();
        String[] splitMessage;
        Boolean startGame = false;

        // get connection of two players
        while (!startGame) {
            String message = DirectUDP.receive(PORT);
            splitMessage = message.split(" ");
            System.out.println("Message split by space is:" + splitMessage[0] + " " + splitMessage[1]);


            // add to list
            if (splitMessage[0].equals("ADD")) {
                s1.playerList.add(splitMessage[1]);
                System.out.println(splitMessage[1] + " added to list");
                // TODO: Make server reply when is good

                if (s1.playerList.size() == 2) {
                    startGame = true;
                    System.out.println("Two players connected: Game start");
                }
            }
        }
    }
}

