import java.util.Objects;

public class Client extends Thread{
    private static String serverIP = "10.139.96.80";

    public void run(){
        // listener
        Boolean running = true;
        while (running) {

            String message = null;
            try {
                message = MulticastUDP.receiveMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Received: " + message);
            assert message != null;
            if (Objects.equals(message, "END")){
                running = false;
                System.out.println("Received: we're done here");

            }
        }
    }

    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49158;
        String message;

        (new Client()).start();

        String username = "funed";
        // remove whitespace and non visible characters
        username = username.replaceAll("\\s+","");

        Boolean connected = false;
        while(!connected) {
            try {
                message = "ADD " + username;
                DirectUDP.send(SEVER_PORT, myPort, Client.serverIP, message);
                connected = true;
            } catch (Exception e) {
                myPort = myPort + 1;
            }
        }
        message = DirectUDP.receive(myPort);
        System.out.println("Received: " + message);

    }
}
