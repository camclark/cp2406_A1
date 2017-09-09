import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticastSender {
    public static void main(String[] args) throws Exception{
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");
        String message = "SEND";

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49152);

        socket.send(packet);

        socket.leaveGroup(address);
        socket.close();

    }
}