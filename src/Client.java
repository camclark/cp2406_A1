import java.awt.*;
import java.util.Objects;

public class Client extends Thread implements Runnable {
    private messageData md;


    public Client(messageData md) {
        this.md = md;
    }

    //One Jframe, jpanel for the grid and then another panel if required with proper layout
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
                md.setS(rMessage);
                System.out.println("Game over");
                running = false;
            }

            // winner and max score
        }
    }

    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49158;

        // TODO: fix IP
        String serverIP = "10.0.0.2";
        String localIP = "10.0.0.2";

        String username = "sam";
        // remove whitespace and non visible characters

        username = username.replaceAll("\\s+", "");

        messageData md = new messageData();
        Integer playerNumber = 0, moveX, moveY;
        Boolean connected = false, playing = false;
        String[] splitMessage, moveInformation;
        String message;

        (new Client(md)).start();
        // TODO: fix bike colours
        Color[] bikeColors = new Color[4];
        bikeColors[0] = Color.black;
        bikeColors[1] = Color.blue;
        bikeColors[2] = Color.red;
        bikeColors[3] = Color.green;

        while (!connected) {
            try {
                message = "ADD " + username + " " + localIP;
                DirectUDP.send(SEVER_PORT, myPort, serverIP, message);

                System.out.println("My IP is: " + localIP + " My port is:" + myPort);
                message = DirectUDP.receive(myPort);
                System.out.println("Received: " + message);
                splitMessage = message.split(" ");
                if (splitMessage[0].equals("OKAY")) {
                    playerNumber = Integer.parseInt(splitMessage[2]);
                    connected = true;
                }
            } catch (Exception e) {
                myPort = myPort + 1;
            }
        }

        System.out.println(username + " you are player " + playerNumber);
        ClientGUI cg = new ClientGUI(playerNumber, serverIP);


        while (!playing) {
            String thing = md.getS();
            if (thing == null || !thing.equals("START")) {
                System.out.println("Waiting for players to connect");
                Thread.sleep(2000);
            } else if (thing.equals("START")) {
                for (int i = 5; i > 0; i--) {
                    System.out.println("Game start in " + i);
                    Thread.sleep(1000);
                }
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
                        i = i + 1;
                        moveInformation = aSplitMessage.split(",");

                        moveX = Integer.parseInt(moveInformation[1]);
                        moveY = Integer.parseInt(moveInformation[2]);
//                        System.out.println("Attempted update X:" + moveX + " Y:" + moveY);

                        cg.t.update(moveX, moveY, bikeColors[i]);
                        if (i == playerNumber){
                            cg.b.move(moveX, moveY);
                        }
                    }
                    cg.refresh();
                    Thread.sleep(100);
                } else if (splitMessage[0].equals("END")){
                    playing = false;
                }
            }
        }
    }
}
