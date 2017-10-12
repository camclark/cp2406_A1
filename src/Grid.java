import java.util.ArrayList;

public class Grid{

    /* Height used as a constant in calculating the size of the visible GUI and the grid array. */
    private final int GRID_HEIGHT = 50;
    private final int GRID_WIDTH = 50;

    /* Two-dimensional grid to keep track of which bikes have "claimed" which
       spots on the visible grid. */
    int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];

    // static bikes for the moment
    ArrayList<LightCycle> bikeList = new ArrayList<>();

//    LightCycle player1 = new LightCycle(1, 0, 0, 1, grid);
//    LightCycle player2 = new LightCycle(1, 1, 1, 2, grid);


    public Grid() {
        //Set grid to 0
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                grid[x][y] = 0;
            }
        }
    }

    public int getGRID_HEIGHT() {
        return GRID_HEIGHT;
    }

    public int getGRID_WIDTH() {
        return GRID_WIDTH;
    }

    public void printGrid() {
        for (int y = 0; y < GRID_HEIGHT; y++) {
            System.out.println();
            for (int x = 0; x < GRID_WIDTH; x++) {
                System.out.print(grid[x][y]);
            }
        }
        System.out.println();

    }

    public void draw(int xPosition, int yPosition, int playerNumber) {
        grid[xPosition][yPosition] = playerNumber;
    }

}
