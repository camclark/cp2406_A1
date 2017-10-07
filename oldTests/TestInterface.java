import java.awt.*;
import javax.swing.*;

public class TestInterface extends JPanel {
    /*
    Test to understand how JPanels and moving objects work.
    Two Ovals of different colours move to the right of the grid
    and reset once they've reached the boundary
     */

    private int x = 0;
    private int y = 0;

    private void moveCar() {
        x = x + 1;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.black);
        g2d.fillOval(x, y, 30, 30);

        g2d.setColor(Color.blue);
        g2d.fillOval(x, y + 50, 30, 30);

    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("Interface Drive Right Loop");
        TestInterface game = new TestInterface();
        frame.add(game);
        frame.setSize(300, 400);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        while (true) {
            game.moveCar();
            game.repaint();
            Thread.sleep(10);

            if(game.x >= 270){
                game.x = 1;
            }
        }
    }
}

