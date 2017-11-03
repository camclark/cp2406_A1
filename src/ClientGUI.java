import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

class ClientGUI extends JFrame {
    final String GAME_OVER_MSG = "GAME OVER";
    static final int POINT_RADIUS = 10; // size of one point
    final int FIELD_WIDTH = 100; // in point
    final int FIELD_HEIGHT = 100;
    final int FIELD_DX = 6; // determined experimentally
    final int FIELD_DY = 28;
    final int START_LOCATION = 200; // late night coding can't recall why I put this, required for set bounds
    final int SHOW_DELAY = 500; // delay for animation
    final int LEFT = 37; // key codes
    final int UP = 38;
    final int RIGHT = 39;
    final int DOWN = 40;
    final int X = 88;
    final int Z = 90;

    JPanel panel = new JPanel(new BorderLayout());
    JLabel statusLabel = new JLabel("Welcome");

    final Color DEFAULT_COLOR = Color.black;
    Canvas canvas = new Canvas();

    public Trail t = new Trail();
    public GuiBike b = new GuiBike();


    boolean gameOver = false;


//    // TODO: modify keylistener
//    public void keyPressed(KeyEvent e) {
//        int keyCode = e.getKeyCode();
//        switch( keyCode ) {
//            case KeyEvent.VK_UP:
//                // handle up
//                break;
//            case KeyEvent.VK_DOWN:
//                // handle down
//                break;
//            case KeyEvent.VK_LEFT:
//                // handle left
//                break;
//            case KeyEvent.VK_RIGHT :
//                // handle right
//                break;
//        }
//    }

    ClientGUI(int playerNumber, String serverIP) {
        setBackground(Color.BLACK);
        setTitle("Tron by Cameron");
        add(panel);
//        setSize(, 1000);
        setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * POINT_RADIUS + FIELD_DX, FIELD_HEIGHT * POINT_RADIUS + FIELD_DY);

//        panel.add(canvas, BorderLayout.NORTH);
//        panel.add(statusLabel, BorderLayout.SOUTH);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        panel.setBounds(START_LOCATION, START_LOCATION, FIELD_WIDTH * POINT_RADIUS + FIELD_DX, FIELD_HEIGHT * POINT_RADIUS + FIELD_DY);
        setResizable(false);
//        canvas.setBackground(Color.white);
        add(BorderLayout.CENTER, canvas);
        statusLabel.setHorizontalAlignment(JLabel.CENTER);
        add(BorderLayout.SOUTH, statusLabel);


        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                String message = null;
                int myPort = 49147;
                if (e.getKeyCode() == LEFT) {
                    System.out.println("Left");
                    message = "TURN WEST";
                } else if (e.getKeyCode() == UP) {
                    System.out.println("Up");
                    message = "TURN NORTH";

                } else if (e.getKeyCode() == RIGHT) {
                    System.out.println("Right");
                    message = "TURN EAST";

                } else if (e.getKeyCode() == DOWN) {
                    System.out.println("Down");
                    message = "TURN SOUTH";
//                } else if (e.getKeyCode() == X){
//                    System.out.println("Toggle speed");
//                    message = "TOGGLE TRAIL";
                } else if (e.getKeyCode() == Z) {
                    System.out.println("Toggle speed");
                    message = "TOGGLE SPEED";
                }

                // todo: change to player number
                Boolean sent = false;
                while (!sent) {
                    try {
                        // needs playerNumber
                        DirectUDP.send(49152, myPort, serverIP, "USER " + playerNumber + " " + message);
                        sent = true;
                        System.out.println("Sent " + message);
                        // USER player# TURN direction#

                    } catch (Exception e1) {
                        myPort = myPort + 1;
                    }
                }
            }
        });

        setVisible(true);
    }

    public void refresh() {
        canvas.repaint();
    }

    public static class GuiBike {
        int x = -1;
        int y = -1;


        public GuiBike() {
        }

        public void move(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void paint(Graphics g) {
            g.setColor(Color.black);
            g.fill3DRect(getX() * POINT_RADIUS, getY() * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS, true); // fillOval()
        }

        int getX() {
            return x;
        }

        int getY() {
            return y;
        }
    }


    public static class Trail {
        public ArrayList<Point> trail = new ArrayList<Point>();


        public Trail() {
        }

        public void update(int x, int y, Color bikeColor) {
            trail.add(new Point(x, y, bikeColor));

        }

        void paint(Graphics g) {
            int i = 0;
            for (Point point : trail) {
                point.paint(g);
                if (i == trail.size() - 1) {
                    point.paintHead(g);
                }
            }


        }
    }

    static class Point {
        protected int x, y;
        protected Color color;


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
            g.fill3DRect(getX() * POINT_RADIUS, getY() * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS, true); // fillOval()
        }
        void paintHead(Graphics g) {
            g.setColor(Color.black);
            g.fill3DRect(getX() * POINT_RADIUS, getY() * POINT_RADIUS, POINT_RADIUS, POINT_RADIUS, true); // fillOval()
        }
    }

    class Canvas extends JPanel {
        @Override
        public void paint(Graphics g) {
            super.paint(g);

            // paint each bike trail
            t.paint(g);
            // paint player bike head
            b.paint(g);

            if (gameOver) {
                g.setColor(Color.red);
                g.setFont(new Font("Arial", Font.BOLD, 60));
                FontMetrics fm = g.getFontMetrics();
                g.drawString(GAME_OVER_MSG, (FIELD_WIDTH * POINT_RADIUS + FIELD_DX - fm.stringWidth(GAME_OVER_MSG)) / 2, (FIELD_HEIGHT * POINT_RADIUS + FIELD_DY) / 2);
            }
        }
    }
}