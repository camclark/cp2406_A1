public class Client {
    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49158;

        String username = "speeds";
        String message = "ADD " + username;
        DirectUDP.send(SEVER_PORT, myPort, "10.0.0.31", message);

        message = DirectUDP.receive(myPort);
        System.out.println("Received: " + message);

    }
}
