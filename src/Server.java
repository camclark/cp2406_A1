public class Server {
    public static void main(String[] args) {
        Grid newGrid = new Grid();
        newGrid.showGrid();

        for (int i=0; i < 5; i++){
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnSouth();

        for (int i=0; i < 5; i++){
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnWest();

        for (int i=0; i < 4; i++){
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.player1.turnNorth();

        for (int i=0; i < 4; i++){
            newGrid.player1.move();
            newGrid.draw(newGrid.player1.xPosition, newGrid.player1.yPosition, newGrid.player1.playerNumber);
        }

        newGrid.showGrid();

        // last move to crash
        newGrid.player1.move();


    }
}
