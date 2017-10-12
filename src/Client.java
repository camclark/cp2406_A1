public class Client {
    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49155;

        String username = "Sam";
        String message = "ADD " + username;
        DirectUDP.send(SEVER_PORT, myPort, "10.0.0.2", message);

        message = DirectUDP.receive(myPort);
        System.out.println(message);

    }
}
