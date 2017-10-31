import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.*;
import javax.swing.plaf.ColorUIResource;
import java.util.*;
import java.io.*;

class ClientGUI extends JFrame {
    final String GAME_OVER_MSG = "GAME OVER";
    static final int POINT_RADIUS = 10; // size of one point
    final int FIELD_WIDTH = 40; // in point
    final int FIELD_HEIGHT = 40;
    final int FIELD_DX = 6; // determined experimentally
    final int FIELD_DY = 28;
    final int START_LOCATION = 200; // late night coding can't recall why I put this, required for set bounds
    final int SHOW_DELAY = 500; // delay for animation
    final int LEFT = 37; // key codes
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    final Color DEFAULT_COLOR = Color.blue;
    Canvas canvas = new Canvas();

    public Trail trail1 = new Trail();


    boolean gameOver = false;

    ClientGUI(int playerNumber) {
        setTitle("Tron by Cameron");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * POINT_RADIUS + FIELD_DX, FIELD_HEIGHT * POINT_RADIUS + FIELD_DY);
        setResizable(false);
        canvas.setBackground(Color.white);
        add(BorderLayout.CENTER, canvas);
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String message = null;
                int myPort = 49147;
                if (e.getKeyCode() == LEFT) {
                    System.out.println("Left");
                    message = "WEST";
                } else if (e.getKeyCode() == UP) {
                    System.out.println("Up");
                    message = "NORTH";

                } else if (e.getKeyCode() == RIGHT) {
                    System.out.println("Right");
                    message = "EAST";

                } else if (e.getKeyCode() == DOWN) {
                    System.out.println("Down");
                    message = "SOUTH";

                }

                // todo: change to player number
                Boolean sent = false;
                while (!sent) {
                    try {
                        // needs playerNumber
                        DirectUDP.send(49152, myPort, "10.0.0.2", "USER " + playerNumber + " TURN " + message);
                        sent = true;
                        System.out.println("Sent " + message);
                        // USER player# TURN direction#

                    } catch (Exception e1) {
                        myPort = myPort + 1;
                    }
                }

//
//            // For later sending messages with key press
        }
    });

    setVisible(true);
}

    public void refresh() { // main loop of game
//        while (!gameOver) {
//            bike.move();
        canvas.repaint();
//            try {
//                Thread.sleep(SHOW_DELAY);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

public static class Trail {
    public ArrayList<Point> trail = new ArrayList<Point>();


    public Trail() {
    }

    public void update(int x, int y, Color bikeColor) {
        trail.add(new Point(x, y, bikeColor));

    }

    void paint(Graphics g) {
        for (Point point : trail) point.paint(g);
    }
}

static class Point {
    protected int x, y;
    protected Color color = Color.blue;

//    public Point(int x, int y) {
//        setXY(x, y);
//        this.color = Color.blue;
//
//    }

    public Point(int x, int y, Color color) {
        setXY(x, y);
        this.color = color;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    void paint(Graphics g) {
        g.setColor(color);
        g.fill3DRect(getX() * POINT_RADIUS + 1, getY() * POINT_RADIUS + 1, POINT_RADIUS - 2, POINT_RADIUS - 2, true); // fillOval()
    }
}

class Canvas extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        // paint each bike
        trail1.paint(g);

        if (gameOver) {
            g.setColor(Color.red);
            g.setFont(new Font("Arial", Font.BOLD, 60));
            FontMetrics fm = g.getFontMetrics();
            g.drawString(GAME_OVER_MSG, (FIELD_WIDTH * POINT_RADIUS + FIELD_DX - fm.stringWidth(GAME_OVER_MSG)) / 2, (FIELD_HEIGHT * POINT_RADIUS + FIELD_DY) / 2);
        }
    }
}
}