public class Client {
    public static void main(String[] args) throws Exception {
        int PORT = 49152;
        int myPort = 49153;

        String username = "Sped";
        String message = "ADD " + username;
        DirectUDP.send(PORT, myPort, "10.0.0.2", message);

        message = DirectUDP.receive(myPort);
        System.out.println(message);

    }
}
