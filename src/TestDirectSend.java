import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestDirectSend {
    public static void main(String[] args) throws Exception{
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        DatagramSocket socket = new DatagramSocket(49153);
        String message = "hello!";

        InetAddress address = InetAddress.getByName("10.139.96.80");
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49152);
        socket.send(packet);
        socket.close();
    }
}
