package zhy2002.springexamples.testing;

/**
 * Provide some static methods for testing.
 */
public class TestUtil {

    public static void wait(int milliSeconds) {
        try{
            Thread.sleep(milliSeconds);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
