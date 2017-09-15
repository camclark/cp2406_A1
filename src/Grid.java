import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    /* Height used as a constant in calculating the size of the visible GUI and the grid array. */
    private final int GRID_HEIGHT = 10;
    private final int GRID_WIDTH = 10;

    /* Two-dimensional grid to keep track of which bikes have "claimed" which
       spots on the visible grid. */
    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];

//    /* Number of pixels that make up the width of the graphical grid. */
//    private final int WIDTH = GRID_WIDTH * 5;
//    private final int HEIGHT = GRID_HEIGHT * 5;

    // static bikes for the moment
    LightCycle player1 = new LightCycle(1, 0, 0, 1, grid);

    public Grid() {
        setPreferredSize(new Dimension(WIDTH + 5, HEIGHT + 5)); // allow all grid lines to be shown

        //Set grid to 0
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                grid[x][y] = 0;
            }
        }
    }


    public void showGrid() {
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
