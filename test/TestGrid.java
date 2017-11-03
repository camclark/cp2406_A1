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

        // check to see if bike can move horizontally
        g.bikeList.get(0).move();
        assert g.bikeList.get(0).xPosition == 3;

        // check to see if bike can move vertically
        g.bikeList.get(1).move();
        assert g.bikeList.get(1).yPosition == 4;

        // check to see if bike can turn
        g.bikeList.get(0).turnSouth();
        assert g.bikeList.get(0).direction == 2;

        // check to see if bike can toggle speed to fast
        g.bikeList.get(0).toggleSpeed();
        assert g.bikeList.get(0).fastSpeed;

        // check to see if bike can toggle speed back to slow
        g.bikeList.get(0).toggleSpeed();
        assert !g.bikeList.get(0).fastSpeed;
    }
}
