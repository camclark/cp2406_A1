
public class LightCycle {
    // Directions are labeled clockwise
    private static final int DIRECTION_NORTH = 0;
    private static final int DIRECTION_EAST = 1;
    private static final int DIRECTION_SOUTH = 2;
    private static final int DIRECTION_WEST = 3;

    public int xPosition;
    public int yPosition;
    public int playerNumber;
    int direction;
    private int[][] gameGrid;
    private boolean cycleAlive = true;


    public LightCycle(int initialDirection, int xPosition, int yPosition, int playerNumber, int[][] gameGridList) {
        direction = initialDirection;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.playerNumber = playerNumber;
        gameGrid = gameGridList;
    }

    // To turn in the opposite direction to which you were coming is an illegal move as the bike will crash
    // For later logic with key listeners
    public void turnWest() {
        if (direction != DIRECTION_EAST) {
            direction = DIRECTION_WEST;
        }
    }

    public void turnEast() {
        if (direction != DIRECTION_WEST) {
            direction = DIRECTION_EAST;
        }
    }

    public void turnSouth() {
        if (direction != DIRECTION_NORTH) {
            direction = DIRECTION_SOUTH;
        }
    }

    public void turnNorth() {
        if (direction != DIRECTION_SOUTH) {
            direction = DIRECTION_NORTH;
        }
    }

    private boolean validMove(int x, int y) {
        // Ensure move is within gameBoard and not on an already occupied quadrant
        return gameGrid[x][y] == 0 && x >= 0 && x < gameGrid.length && y >= 0 && y < gameGrid[0].length;

    }

    public void move() {
        switch (direction) {
            case DIRECTION_NORTH:
                cycleAlive = validMove(xPosition, yPosition - 1);
                if (cycleAlive) {
                    yPosition--;
                }
                break;


            case DIRECTION_EAST:
                cycleAlive = validMove(xPosition + 1, yPosition);
                if (cycleAlive) {
                    xPosition++;
                }
                break;

            case DIRECTION_SOUTH:
                cycleAlive = validMove(xPosition, yPosition + 1);
                if (cycleAlive) {
                    yPosition++;
                }
                break;

            case DIRECTION_WEST:
                cycleAlive = validMove(xPosition - 1, yPosition);
                if (cycleAlive) {
                    xPosition--;
                }
                break;
            // kill bike

        }
        if (!cycleAlive) {
            System.out.print("player " + playerNumber + " has crashed");
            // TODO: Terminate bike
        }
    }
}
