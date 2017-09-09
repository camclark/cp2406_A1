public class Server {
    public static void main(String[] args) {
        Grid newGrid = new Grid();
        LightCycle player1 = new LightCycle(1, 0, 0, 1);

        newGrid.showGrid();

        for (int i=0; i < 5; i++){
            player1.move();
            newGrid.draw(player1.xPosition, player1.yPosition, player1.playerNumber);
        }

        player1.turnSouth();

        for (int i=0; i < 5; i++){
            player1.move();
            newGrid.draw(player1.xPosition, player1.yPosition, player1.playerNumber);
        }

        newGrid.showGrid();

    }
}
