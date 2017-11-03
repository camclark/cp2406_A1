import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestFileHandler {
    // should throw no errors, ie file not found or cannot write to fike
    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void testCreate() throws Exception {
        FileHandler fh = new FileHandler();
        //check that the file does not crash
        assert !expectedEx.equals(ExpectedException.none());

        //check that there are more then one score saved
        assert fh.scoreList.size() > 0;

        //check that the formatting is the same for all entries
        assert fh.scoreList.get(0).split(" ").length == 2;
    }
}