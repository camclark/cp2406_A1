import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Server {
    public static void main(String[] args) throws Exception {
        Server newServer = new Server();
        Grid newGrid = new Grid();
        newGrid.showGrid();

        for (int i = 0; i < 5; i++) {
            moveDrawAndSendTest(newServer, newGrid);
        }

        newGrid.player1.turnSouth();

        for (int i = 0; i < 5; i++) {
            moveDrawAndSendTest(newServer, newGrid);
        }

        newGrid.player1.turnWest();

        for (int i = 0; i < 4; i++) {
            moveDrawAndSendTest(newServer, newGrid);
        }

        newGrid.player1.turnNorth();

        for (int i = 0; i < 4; i++) {
            moveDrawAndSendTest(newServer, newGrid);
        }

        newGrid.showGrid();
        try {
            newServer.broadcast(newGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // last move to crash
        newGrid.player1.move();
    }

    private static void moveDrawAndSendTest(Server newServer, Grid newGrid) {
        newGrid.player1.move();
        newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        try {
            newServer.broadcast(newGrid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void broadcast(Grid newGrid) throws Exception{
        System.setProperty("java.net.preferIPv4Stack", "true");
        String message = getPositionBroadcast(newGrid);

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49152);

        socket.send(packet);

        socket.leaveGroup(address);
        socket.close();

    }

    private String getPositionBroadcast(Grid newGrid){
        return newGrid.player1.playerNumber + "," + newGrid.player1.xPosition + "," + newGrid.player1.yPosition;
    }


}
