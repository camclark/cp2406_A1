import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class TestMulticastMovement {
    /*
    Bike moves clockwise around the grid then crashes at original position
    As each bike has a velocity only change in direction is broadcast
     */

    public static void main(String[] args) throws Exception {
        TestMulticastMovement player = new TestMulticastMovement();
        Grid newGrid = new Grid();
        newGrid.showGrid();

        for (int i = 0; i < 5; i++) {
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnSouth();
        player.broadcastMovementChange(newGrid);

        for (int i = 0; i < 5; i++) {
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnWest();
        player.broadcastMovementChange(newGrid);

        for (int i = 0; i < 4; i++) {
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnNorth();
        player.broadcastMovementChange(newGrid);

        for (int i = 0; i < 4; i++) {
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.showGrid();

        // last move to crash
        newGrid.player1.move();
    }


    private void broadcastMovementChange(Grid newGrid) throws Exception{
        System.setProperty("java.net.preferIPv4Stack", "true");
        String message = newGrid.player1.playerNumber + "," + newGrid.player1.direction;

        InetAddress address = InetAddress.getByName("228.5.8.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), address, 49152);

        socket.send(packet);

        socket.leaveGroup(address);
        socket.close();

    }
}
