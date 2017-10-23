import java.net.InetAddress;
import java.util.Objects;

public class Client extends Thread implements Runnable{
    private static String serverIP = "10.0.0.31";
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
        String localIP = "10.0.0.31";
        messageData md = new messageData();

        (new Client(md)).start();



        String username = "hello";
        // remove whitespace and non visible characters
        username = username.replaceAll("\\s+", "");


        Boolean connected = false;
        Boolean playing = false;

        String[] splitMessage;
        String[] moveInformation;
        int moveX, moveY;
        ClientGUI cg = new ClientGUI();



//        cg.trail1.update(10, 20);
//        cg.trail1.update(10, 21);


        String message;

        while (!connected) {
            try {
                message = "ADD " + username + " " + localIP;
                DirectUDP.send(SEVER_PORT, myPort, serverIP, message);
                connected = true;

                System.out.println("My IP is: " + localIP + " My port is:" + myPort);
                message = DirectUDP.receive(myPort);
                System.out.println("Received: " + message);
            } catch (Exception e) {
                myPort = myPort + 1;
            }
        }

        System.out.println("not playing yet");
//        cg.trail1.update(10, 22);

        while (!playing) {
            String thing = md.getS();
            if (thing == null) {
                System.out.println("Waiting for players to connect");
                Thread.sleep(1000);
            } else if (thing.equals("START")) {
                System.out.println("Game start!");
                playing = true;
            }
        }

//        while(playing) {
//            // update ClientGui? hack job for the moment
//            // not using thread, updated later
////            message = MulticastUDP.receiveMessage();
////            md.getS()
//            message = md.getS();
//            splitMessage = message.split(" ");
//            if (splitMessage.length > 1) {
//                for (for ) {
//                    moveInformation = aSplitMessage.split(",");
//
//                    moveX = Integer.parseInt(moveInformation[1]);
//                    moveY = Integer.parseInt(moveInformation[2]);
//                    System.out.println("SPLITTIES x:" + moveX + " y:" + moveY);
//
//                    cg.trail1.update(moveX, moveY);
//                }
//            }
//        }


        // update ClientGui? hack job for the moment
        // not using thread, updated later
//            message = MulticastUDP.receiveMessage();
//            md.getS()

        while (playing) {
//            cg.trail1.update(10, 24);

            message = md.getS();
            if (message != null) {
                splitMessage = message.split(" ");
                if (splitMessage.length > 1) {
                    for (String aSplitMessage : splitMessage) {
                        moveInformation = aSplitMessage.split(",");

                        moveX = Integer.parseInt(moveInformation[1]);
                        moveY = Integer.parseInt(moveInformation[2]);
                        System.out.println("Attempted update X:" + moveX + " Y:" + moveY);

                        cg.trail1.update(moveX, moveY);
                    }
                    cg.run();
                    Thread.sleep(500);
                }
            }
        }
    }
}
