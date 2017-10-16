import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticastReader {
    public static void main(String[] args) throws Exception {
        testMethod();

    }

    private static void testMethod() throws IOException {
        // compatible with TestMulticastMovement and TestMulticastSender

        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49150);

        socket.joinGroup(address);

        Boolean runSwitch = true;
        while (runSwitch) {
            byte[] messageBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);

            // blocking statement
            socket.receive(receivePacket);

            // trim removes nulls from message buffer
            String resultStr = new String(messageBuffer).trim();

            if (resultStr.equals("END")){
                runSwitch = false;
            } else{
                System.out.println("received: " + resultStr);
            }
        }

        socket.leaveGroup(address);
        socket.close();
    }
}