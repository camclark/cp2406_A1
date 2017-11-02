import java.util.ArrayList;

public class Grid{

    /* Height used as a constant in calculating the size of the visible GUI and the grid array. */
    private final int GRID_HEIGHT = 40;
    private final int GRID_WIDTH = 40;

    /* Two-dimensional grid to keep track of which bikes have "claimed" which
       spots on the visible grid. */
    int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];

    // static bikes for the moment
    ArrayList<LightCycle> bikeList = new ArrayList<>();

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

    public Integer countPlayerTrail(int playerNumber){
        int count = 0;
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (grid[x][y] == playerNumber){
                    count++;
                }
            }
        }
        return count;
    }

    public void draw(int xPosition, int yPosition, int playerNumber) {
        grid[xPosition][yPosition] = playerNumber;
    }

}
