import java.net.InetAddress;
import java.util.Objects;

public class Client extends Thread{
    private static String serverIP = "10.0.0.2";
    private messageData md;


    //One Jframe, jpanel for the grid and then another panel if required with proper layour
    public void run(messageData md){
        // listener
        Boolean running = true;
        while (running) {

            String message = null;
            try {
                message = MulticastUDP.receiveMessage();
                md.setS(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
            assert message != null;
            if (Objects.equals(message, "END")){
                running = false;
                System.out.println("Received: we're done here");
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int SEVER_PORT = 49152;
        int myPort = 49158;
//        InetAddress localIP = InetAddress.getLocalHost();
        // TODO: fix IP
        String localIP = "10.0.0.2";
        messageData md = new messageData();

        (new Client()).start();

        String username = "cam";
        // remove whitespace and non visible characters
        username = username.replaceAll("\\s+","");


// todo: make enum so i can do better states
        Boolean connected = false;
        Boolean playing = true;

        String[] splitMessage;
        String[] moveInformation;
        int moveX, moveY;
        ClientGUI cg = new ClientGUI();
        cg.trail1.update(20,20);
        String message;

        while(!connected) {
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


        while(playing) {
            // update ClientGui? hack job for the moment
            // not using thread, updated later
            // TODO: update to use thread for playing
//            message = MulticastUDP.receiveMessage();
//            md.getS()
            message = md.getS();
            splitMessage = message.split(" ");
            if (splitMessage.length > 1) {
                for (String aSplitMessage : splitMessage) {
                    moveInformation = aSplitMessage.split(",");

                    moveX = Integer.parseInt(moveInformation[1]);
                    moveY = Integer.parseInt(moveInformation[2]);
                    System.out.println("SPLITTIES x:" + moveX + " y:" + moveY);

                    cg.trail1.update(moveX, moveY);
                }
            }
        }
    }
}
