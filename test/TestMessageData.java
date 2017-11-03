import org.junit.Test;

public class TestMessageData {
    @Test

    public void TestMessageData() throws Exception {
        messageData md = new messageData();

        md.setS("Test");
        assert md.getS().equals("Test");
        assert !md.getS().equals("test");
    }
}