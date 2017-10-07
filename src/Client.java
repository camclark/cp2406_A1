public class Client {
    public static void main(String[] args) throws Exception {
        int PORT = 49152;
        int myPort = 49153;

        String username = "Leed";
        String message = "ADD " + username;
        DirectUDP.send(PORT, myPort, "10.0.0.31", message);
    }
}
