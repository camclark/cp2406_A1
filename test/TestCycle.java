import org.junit.Test;

public class TestCycle {
    @Test
    public void testLightCycle() throws Exception {
        Grid g = new Grid();
        Server newServer = new Server(g);

        newServer.playerList.add(0, new Player("Dan", "10.10.10.3", 4222, 1));
        newServer.playerList.add(1, new Player("Cam", "10.10.10.4", 4223, 2));

        // check to see if players were added to list
        assert newServer.playerList.size() > 0;
    }
}
