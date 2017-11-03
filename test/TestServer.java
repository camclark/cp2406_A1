import org.junit.Test;

public class TestServer {
    @Test
    public void testCreate() throws Exception {
        Grid g = new Grid();
        Server newServer = new Server(g);
    }
}
