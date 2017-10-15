import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastUDP extends Thread {
    static void sendMessage(String message) throws IOException {
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49150);

        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49150);

        socket.send(packet);

        socket.close();

    }

    static String receiveMessage() throws IOException {
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress address = InetAddress.getByName("228.5.8.8");
        MulticastSocket socket = new MulticastSocket(49150);

        socket.joinGroup(address);
        String resultStr;

        byte[] messageBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);

        // blocking statement
        socket.receive(receivePacket);

        // trim removes nulls from message buffer
        resultStr = new String(messageBuffer).trim();

        System.out.println("Received: " + resultStr);

        socket.leaveGroup(address);
        socket.close();

        return resultStr;
    }
}