
import java.net.DatagramPacket;
        import java.net.DatagramSocket;
        import java.net.InetAddress;

public class DirectUDP {
    public static String receive(int port) throws Exception {
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        DatagramSocket socket = new DatagramSocket(port);

        byte[] messageBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);

        socket.receive(receivePacket);

        String message = new String(messageBuffer).trim();

        socket.close();
        return message;

    }

    public static void send(int receiver, int sender, String ip, String message) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");

        //add receiver and sender to packet
        message = message + " " + receiver + " " + sender;

        DatagramSocket socket = new DatagramSocket(sender);

        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, receiver);
        socket.send(packet);
        socket.close();

    }
}


