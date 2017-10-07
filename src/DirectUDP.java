
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

//        //test bs
//
//        System.setProperty("java.net.preferIPv4Stack", "true");
//
//        InetAddress localIP = InetAddress.getLocalHost();
//        System.out.println("my ip is: " + localIP.getHostAddress());
//
//        DatagramSocket socket = new DatagramSocket(49152);
//
//        byte[] messageBuffer = new byte[1024];
//        DatagramPacket receivePacket = new DatagramPacket(messageBuffer, 1024);
//
//        socket.receive(receivePacket);
//
//        String message = new String(messageBuffer).trim();
//        System.out.println(message);
//
//        socket.close();
    }

    public static void send(int port, int myPort, String ip, String message) throws Exception {
        System.setProperty("java.net.preferIPv4Stack", "true");

        DatagramSocket socket = new DatagramSocket(myPort);

        InetAddress address = InetAddress.getByName(ip);
        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, port);
        socket.send(packet);
        socket.close();


//        // test bs
//        System.setProperty("java.net.preferIPv4Stack", "true");
//
//        DatagramSocket socket = new DatagramSocket(49153);
//        String message = "hello!";
//
//        InetAddress address = InetAddress.getByName("10.0.0.31");
//        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49152);
//        socket.send(packet);
//        socket.close();
    }
}


