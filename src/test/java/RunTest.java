import org.junit.Test;
import static org.junit.Assert.*;

public class RunTest {
    @Test
    public void RunTest() {
        HelloWorld h = new HelloWorld();
        String str = "Error";

        assertEquals(str, h.Hello(str));
    }
}
