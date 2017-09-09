import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMultiCastReader {
    public static void main(String[] args) throws Exception {
        // fixes mac OS bug with wireless internet use
        System.setProperty("java.net.preferIPv4Stack", "true");

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        Boolean runSwitch = Boolean.TRUE;
        while (runSwitch == Boolean.TRUE) {
            byte[] messageBuffer = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);

            // blocking statement
            socket.receive(receivePacket);

            String resultStr = new String(messageBuffer).trim();

            if (resultStr.equals("END")){
                runSwitch = Boolean.FALSE;
            } else{
                System.out.println("received: " + resultStr);
            }


        }

        socket.leaveGroup(address);
        socket.close();

    }
}