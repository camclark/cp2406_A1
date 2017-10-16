import java.util.ArrayList;
import java.net.InetAddress;
import java.util.List;
import java.lang.reflect.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Server {
    private ArrayList<Player> playerList = new ArrayList<>();
    // eg of how to get from ArrayList -  server.playerList.get(0).getUsername()


    // the boolean that will be turned of to stop the server
    private boolean runSwitch;

    /*
     *  server constructor that receive the port to listen to for connection as parameter
     *  in console
     */
    private Server() throws Exception {
        InetAddress LOCAL_IP = InetAddress.getLocalHost();
    }


    public static void main(String[] args) throws Exception {
        int MY_PORT = 49152;
        // change later to as many as needed
        int playersRequired = 3;

        Server server = new Server();

        // show local IP
        InetAddress localIP = InetAddress.getLocalHost();
        System.out.println("my ip is: " + localIP.getHostAddress());

        // get connection of two players
        connector(MY_PORT, server, playersRequired);

        // start new game
        Grid newGrid = new Grid();
        int gridHeight = newGrid.getGRID_HEIGHT();
        int gridWidth = newGrid.getGRID_WIDTH();

        for (int i = 0; i < server.playerList.size(); i++) {
            // origin and end - 1  to avoid starting on edge
            int xPosition = ThreadLocalRandom.current().nextInt(1, gridWidth - 1);
            int yPosition = ThreadLocalRandom.current().nextInt(1, gridHeight - 1);
            int direction = ThreadLocalRandom.current().nextInt(0, 3 + 1);


            // player number is +1 as 0 on board indicates empty spaces
            newGrid.bikeList.add(new LightCycle(direction, xPosition, yPosition, i + 1, newGrid.grid));
            System.out.println("Added bike " + (i + 1));
        }

        // play game -  if bikes go for same tile at same time wont die
        Boolean game = true;
        newGrid.printGrid();
        while (game) {
            moveEachBike(server, newGrid);
            drawEachBike(server, newGrid);

            // sleep and give players a chance to think
            Thread.sleep(250);

            // Position broadcast message eg: Jack,10,10 Jill,12,10 Tron,10,14
            StringBuilder message = getPlayerPositionsMessage(server, newGrid);
            MulticastUDP.sendMessage(message.toString());

            newGrid.printGrid();
            game = isWinner(server, newGrid);
        }

        System.out.println("Player " + getWinningBikeNumber(server, newGrid) + " wins!");
        String message = "Player " + getWinningBikeNumber(server, newGrid) + " wins!";
        MulticastUDP.sendMessage(message);

        // end game
        message = "END";
        MulticastUDP.sendMessage(message);

    }

    private static Boolean isWinner(Server server, Grid newGrid) {
        Boolean game = true;
        if (numberBikesAlive(server, newGrid) <= 1) {
            game = false;
        }
        return game;
    }

    private static StringBuilder getPlayerPositionsMessage(Server server, Grid newGrid) {
        StringBuilder message  = new StringBuilder();
        for (int i = 0; i < server.playerList.size(); i++) {
            Player player = server.playerList.get(i);
            LightCycle bike = newGrid.bikeList.get(i);
            message.append(player.getUsername()).append(",").append(bike.xPosition).append(",").append(bike.yPosition).append(" ");
        }
        return message;
    }

    private static Integer getWinningBikeNumber(Server server, Grid newGrid) {
        for (int i = 0; i < server.playerList.size(); i++) {
            if (newGrid.bikeList.get(i).cycleAlive) {
                return newGrid.bikeList.get(i).playerNumber;
            }
        }
        // needed? otherwise no return?
        return 0;
    }


        private static Integer numberBikesAlive(Server server, Grid newGrid) {
        int count = 0;
        for (int i = 0; i < server.playerList.size(); i++) {
            if (newGrid.bikeList.get(i).cycleAlive) {
                count = count + 1;
            }
        }
        return count;
    }

    private static void moveEachBike(Server server, Grid newGrid) {
        for (int i = 0; i < server.playerList.size(); i++) {
            newGrid.bikeList.get(i).move();
        }
    }

    private static void drawEachBike(Server server, Grid newGrid) {
        for (int i = 0; i < server.playerList.size(); i++) {
            newGrid.draw(newGrid.bikeList.get(i).xPosition, newGrid.bikeList.get(i).yPosition,
                    newGrid.bikeList.get(i).playerNumber);
        }
    }

    private static void connector(int MY_PORT, Server server, int playersRequired) throws Exception {
        String[] splitMessage;
        Boolean serverFailed;
        Boolean startGame = false;

        while (!startGame) {
            String message = DirectUDP.receive(MY_PORT);
            splitMessage = message.split(" ");


            // show message - for testing
            System.out.println("Message split by space is:");
            for (String aSplitMessage : splitMessage) {
                System.out.print(aSplitMessage + " ");
            }
            System.out.println();

            serverFailed = false;

            // add to list
            if (splitMessage[0].equals("ADD")) {
                String username = splitMessage[1];
                // TODO: fix IP
                String ip = splitMessage[2];

                Integer socket = Integer.parseInt(splitMessage[4]);

                for (int i = 0; i < server.playerList.size(); i++) {
                    if (splitMessage[1].equals(server.playerList.get(i).getUsername())) {
                        serverFailed = true;
                    }
                }

                if (serverFailed) {
                    message = "FAILED userNameTaken";
                    DirectUDP.send(socket, MY_PORT, ip, message);

                    // send a message to client saying that username is taken and to try again
                } else {
                    message = "OKAY";
                    server.playerList.add(new Player(username, ip, socket));
                    DirectUDP.send(socket, MY_PORT, ip, message);
                    System.out.println("Added: " + username);
                }

                if (server.playerList.size() == playersRequired) {
                    startGame = true;
                    System.out.println("Two players connected: Game start");
                }
            }
        }
    }
}

