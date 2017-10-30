import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestDirectUDP {
    public static void main(String[] args) throws Exception{
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress localIP = InetAddress.getLocalHost();
        System.out.println("my ip is: " + localIP.getHostAddress());

        DatagramSocket socket = new DatagramSocket(49152);

        byte[] messageBuffer = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);

        while(true) {
            socket.receive(receivePacket);

            String message = new String(messageBuffer).trim();
            System.out.println(message);

            if (message.equals("END")){
                        socket.close();
            }

        }
    }
}
