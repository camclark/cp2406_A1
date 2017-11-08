import java.io.IOException;
import java.util.ArrayList;
import java.net.InetAddress;
import java.util.concurrent.ThreadLocalRandom;


public class Server extends Thread implements Runnable {
    public ArrayList<Player> playerList = new ArrayList<>();
    // eg of how to get from ArrayList -  server.playerList.get(0).getUsername()
    private Grid g;


    // the boolean that will be turned of to stop the server
    private boolean runSwitch;

    /*
     *  server constructor that receive the port to listen to for connection as parameter
     *  in console
     */
    public Server(Grid g) throws Exception {
        this.g = g;

//        InetAddress LOCAL_IP = InetAddress.getLocalHost();

    }

    //One Jframe, jpanel for the grid and then another panel if required with proper layour
    public void run() {
        String rMessage = null;
        String[] splitMessage;

        // listener
        Boolean running = true;
        while (running) {

            try {
                rMessage = DirectUDP.receive(49152);
                System.out.println(rMessage + ": received in listening thread");


                if (rMessage != null) {
                    splitMessage = rMessage.split(" ");

                    if (splitMessage[0].equals("USER")) {
                        int bikeNumber = Integer.parseInt(splitMessage[1]) - 1;


                        if (splitMessage[2].equals("TURN")) {
                            String direction = splitMessage[3];
                            System.out.println("TURNING: " + direction);
                            // eg msg - USER player# TURN direction#

                            switch (direction) {
                                case "NORTH":
                                    g.bikeList.get(bikeNumber).turnNorth();
                                    break;
                                case "EAST":
                                    g.bikeList.get(bikeNumber).turnEast();
                                    break;
                                case "SOUTH":
                                    g.bikeList.get(bikeNumber).turnSouth();
                                    break;
                                case "WEST":
                                    g.bikeList.get(bikeNumber).turnWest();
                                    break;
                            }
                        }
                        if (splitMessage[2].equals("TOGGLE")) {
                            // eg msg - USER player# TOGGLE SPEED
                            if (splitMessage[3].equals("SPEED")){
                                System.out.println("Toggle speed for bike: " + bikeNumber);
                                g.bikeList.get(bikeNumber).toggleSpeed();
                            }
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws Exception {
        int MY_PORT = 49152;
        // change later to as many as needed
        int playersRequired = 3;
        Grid g = new Grid();
        Server server = new Server(g);

        // show local IP
        InetAddress localIP = InetAddress.getLocalHost();
        System.out.println("my ip is: " + localIP.getHostAddress());

        // get connection of players
        connector(MY_PORT, server, playersRequired);

        // start new game
        int gridHeight = g.getGRID_HEIGHT();
        int gridWidth = g.getGRID_WIDTH();


        for (int i = 0; i < server.playerList.size(); i++) {
            // origin and end - 5  to avoid starting on edge
            int xPosition = ThreadLocalRandom.current().nextInt(5, gridWidth - 5);
            int yPosition = ThreadLocalRandom.current().nextInt(5, gridHeight - 5);
            int direction = ThreadLocalRandom.current().nextInt(0, 3 + 1);


            // player number is +1 as 0 on board indicates empty spaces
            g.bikeList.add(new LightCycle(direction, xPosition, yPosition, i + 1, g.grid));
            System.out.println("Added bike " + (i + 1));
        }

        // wait 2 sec for client to process
        Thread.sleep(2000);

        // wait 5 sec for game start to process
        String message = "START";
        MulticastUDP.sendMessage(message);
        System.out.println("Game start in 5 seconds");
        Thread.sleep(5000);

        // run action listener and game
        (new Server(g)).start();
        playGame(server, g);

//        // get and broadcast winner
//        message = "PLAYER " + getWinningBikeNumber(server, g) + " WINS";
//        System.out.println(message);
//        MulticastUDP.sendMessage(message);

        // // find and send max trail
        message = getMaxTrailLength(g, server);
        System.out.println(message);
        MulticastUDP.sendMessage(message);

        // add to high scores
        new FileHandler();

        // end game

        Thread.sleep(2000);
        System.out.println("Ending game");
        MulticastUDP.sendMessage("END");

    }

    private static String getMaxTrailLength(Grid g, Server server) {
        String message;
        int score, maxScore = 0, maxScorePlayer = 0;

        for (int i = 1; i < server.playerList.size() + 1; i++) {
            score = g.countPlayerTrail(i);
            System.out.println("Counted " + score + " for " + i);
            if (score > maxScore){
                maxScore = score;
                maxScorePlayer = i;
            }
        }
        // 0th player list position converted to player 1 ect
        maxScorePlayer--;
        String maxScoreUsername = server.playerList.get(maxScorePlayer).getUsername();


        Integer winnerNumber = getWinningBikeNumber(server, g) - 1;
        String winnerUsername = server.playerList.get(winnerNumber).getUsername();

        message =  "WINNER: " +  winnerUsername +  " --- MAX SCORE: " + maxScore + " " + maxScoreUsername;
        return message;
    }

    private static void playGame(Server server, Grid newGrid) throws InterruptedException, IOException {
        // play game -  if bikes go for same tile at same time wont die
        Boolean game = true;
//        newGrid.printGrid();
        while (game) {

            moveEachBike(server, newGrid);
            Thread.sleep(120);

            // Position broadcast messageData eg: Jack,10,10 Jill,12,10 Tron,10,14
            StringBuilder positionMessage = getPlayerPositionsMessage(server, newGrid);
            MulticastUDP.sendMessage(positionMessage.toString());
//            newGrid.printGrid();
            game = isWinner(server, newGrid);
            if (!game) {
                break;
            }

            moveEachFastBike(server, newGrid);
            Thread.sleep(125);

            positionMessage = getPlayerPositionsMessage(server, newGrid);
            MulticastUDP.sendMessage(positionMessage.toString());
//            newGrid.printGrid();
            game = isWinner(server, newGrid);

        }
    }

    private static Boolean isWinner(Server server, Grid newGrid) {
        Boolean game = true;
        if (numberBikesAlive(server, newGrid) <= 1) {
            game = false;
        }
        return game;
    }

    private static StringBuilder getPlayerPositionsMessage(Server server, Grid newGrid) {
        StringBuilder message = new StringBuilder();
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
            // move
            newGrid.bikeList.get(i).move();
            // draw trail
            newGrid.draw(newGrid.bikeList.get(i).xPosition, newGrid.bikeList.get(i).yPosition,
                    newGrid.bikeList.get(i).playerNumber);

        }
    }

    private static void moveEachFastBike(Server server, Grid newGrid) {
        for (int i = 0; i < server.playerList.size(); i++) {
            if (newGrid.bikeList.get(i).fastSpeed) {
                // move
                newGrid.bikeList.get(i).move();
                // draw trail
                newGrid.draw(newGrid.bikeList.get(i).xPosition, newGrid.bikeList.get(i).yPosition,
                        newGrid.bikeList.get(i).playerNumber);
            }
        }
    }

    private static void connector(int MY_PORT, Server server, int playersRequired) throws Exception {
        String[] splitMessage;
        Boolean serverFailed;
        Boolean startGame = false;
        Integer playerNumber = 0;


        while (!startGame) {
            String message = DirectUDP.receive(MY_PORT);
            splitMessage = message.split(" ");


            // show messageData - for testing
            System.out.println("Message split by space is:");
            for (String aSplitMessage : splitMessage) {
                System.out.print(aSplitMessage + " ");
            }
            System.out.println();

            serverFailed = false;

            // add to list
            if (splitMessage[0].equals("ADD")) {
                String username = splitMessage[1];
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

                    // send a messageData to client saying that username is taken and to try again
                } else {
                    playerNumber = playerNumber + 1;
                    message = "OKAY " + username + " " + playerNumber;
                    server.playerList.add(new Player(username, ip, socket, playerNumber));
                    DirectUDP.send(socket, MY_PORT, ip, message);


                    System.out.println("Added: " + username + " Player no." + playerNumber + " IP: " + ip + " Socket: " + socket);
                }

                if (server.playerList.size() == playersRequired) {
                    startGame = true;
                    System.out.println(playersRequired + " players connected: Game start");
                }
            }
        }
    }
}

