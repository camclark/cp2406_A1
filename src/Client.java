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
            if (message.equals("END")){
                running = false;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49156;

        (new Client()).start();

        String username = "Speedr";
        String message = "ADD " + username;
        DirectUDP.send(SEVER_PORT, myPort, Client.serverIP, message);

        message = DirectUDP.receive(myPort);
        System.out.println("Received: " + message);

    }
}
