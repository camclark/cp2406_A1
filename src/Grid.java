import javax.swing.*;
import java.awt.*;

public class Grid extends JPanel {

    /* Height used as a constant in calculating the size of the visible GUI and
       the grid array. */
    private final int GRID_HEIGHT = 100;

    /* Width used as a constant in calculating the size of the visible GUI and
       the grid array. */
    private final int GRID_WIDTH = 100;

    /* Two-dimensional grid to keep track of which bikes have "claimed" which
       spots on the visible grid. */
    private int[][] grid = new int[GRID_WIDTH][GRID_HEIGHT];

    /* Number of pixels that make up the width of the graphical grid. */
    private final int WIDTH = GRID_WIDTH * 5;

    /* Number of pixels that make up the height of the graphical grid. */
    private final int HEIGHT = GRID_HEIGHT * 5;


    public Grid() {
        setPreferredSize(new Dimension(WIDTH + 1, HEIGHT + 1));//Plus one to assure the edge line is shown

        //Set grid to 0
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                grid[x][y] = 0;
            }
        }
    }
}
