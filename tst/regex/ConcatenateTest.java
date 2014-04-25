package regex;import org.junit.Test;
import regex.Literal;
import regex.Pattern;

public class ConcatenateTest {

    @Test
    public void test() {
        Pattern pattern = new Concatenate(new Literal('a'), new Literal('b'));
        assertEquals("/ab/", pattern.toRegEx());
        assertFalse(pattern.matches("a"));
        assertTrue(pattern.matches("ab"));
        assertFalse(pattern.matches("abc"));
    }
}
