import java.awt.*;
import java.util.Objects;

public class Client extends Thread implements Runnable{
    private static String serverIP = "10.139.96.80";
    private messageData md;


    public Client(messageData md) {
        this.md = md;
    }

    //One Jframe, jpanel for the grid and then another panel if required with proper layour
    public void run() {
        String rMessage = null;


        // listener
        Boolean running = true;
        while (running) {

            try {
                rMessage = MulticastUDP.receiveMessage();

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (rMessage != null) {
                md.setS(rMessage);
            }
            if (Objects.equals(rMessage, "END")) {
                running = false;
                System.out.println("Game over");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49158;
//        InetAddress localIP = InetAddress.getLocalHost();
        // TODO: fix IP
        String localIP = "10.139.96.80";
        messageData md = new messageData();
        Integer playerNumber = 0;


        (new Client(md)).start();

        Color[] bikeColors = new Color[4];
        bikeColors[0] = Color.black;
        bikeColors[1] = Color.red;
        bikeColors[2] = Color.blue;
        bikeColors[3] = Color.green;

        String username = "fello";
        // remove whitespace and non visible characters
        username = username.replaceAll("\\s+", "");


        Boolean connected = false;
        Boolean playing = false;

        String[] splitMessage;
        String[] moveInformation;

        // TODO: fix bike colours




        int moveX, moveY;
        ClientGUI cg = new ClientGUI();

        String message;

        while (!connected) {
            try {
                message = "ADD " + username + " " + localIP;
                DirectUDP.send(SEVER_PORT, myPort, serverIP, message);

                System.out.println("My IP is: " + localIP + " My port is:" + myPort);
                message = DirectUDP.receive(myPort);
                System.out.println("Received: " + message);
                splitMessage = message.split(" ");
                if (splitMessage[0].equals("OKAY")){
                    playerNumber = Integer.parseInt(splitMessage[2]);
                    connected = true;
                }
            } catch (Exception e) {
                myPort = myPort + 1;
            }
        }

        System.out.println(username + " you are player " + playerNumber);


        while (!playing) {
            String thing = md.getS();
            if (thing == null || !thing.equals("START")) {
                System.out.println("Waiting for players to connect");
                Thread.sleep(2000);
            } else if (thing.equals("START")) {
                System.out.println("Game start in 5 seconds!");
                playing = true;
            }
        }

        while (playing) {
            message = md.getS();
            if (message != null) {
                splitMessage = message.split(" ");
                if (splitMessage.length > 1) {
                    int i = 0;
                    for (String aSplitMessage : splitMessage) {
                        i = i+1;
                        moveInformation = aSplitMessage.split(",");

                        moveX = Integer.parseInt(moveInformation[1]);
                        moveY = Integer.parseInt(moveInformation[2]);
                        System.out.println("Attempted update X:" + moveX + " Y:" + moveY);

                        cg.trail1.update(moveX, moveY, bikeColors[i]);
                    }
                    cg.refresh();
                    Thread.sleep(500);
                }
            }
        }
    }
}
