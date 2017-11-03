import org.junit.Test;

public class TestGrid {
    @Test
    public void testCreate() throws Exception {
        Grid g = new Grid();

        assert g.getGRID_HEIGHT() > 0;
        assert g.getGRID_WIDTH() > 0;

        g.bikeList.add(0, new LightCycle(1,2,3,1, g.grid));
        g.bikeList.add(1, new LightCycle(0,5,5,2, g.grid));

        assert g.bikeList.size() > 0;

        g.bikeList.get(0).move();

        // check to see if bike can move horizontally
        assert g.bikeList.get(0).xPosition == 3;

        g.bikeList.get(1).move();

        // check to see if bike can move vertically
        assert g.bikeList.get(1).yPosition == 4;
    }
}
