import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestNetwork {
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void TestNetwork() throws Exception {
        Grid g = new Grid();
        Server server = new Server(g);
        (new Server(g)).start();


        int SEVER_PORT = 49152, myPort = 49158, playerNumber = 1;
        String serverIP = "10.0.0.2", message = "TURN SOUTH";

        // test sending of message
        DirectUDP.send(49152, myPort, serverIP, "USER " + playerNumber + " " + message);

        //check that there are no exceptions raised
        assert !expectedEx.equals(ExpectedException.none());
    }
}
