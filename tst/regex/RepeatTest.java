package regex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RepeatTest {

    @Test
    public void test() {
        Pattern pattern = new Repeat(new Literal('a'));
        assertEquals("/a*/", pattern.toRegEx());
        assertTrue(pattern.matches(""));
        assertTrue(pattern.matches("a"));
        assertTrue(pattern.matches("aaaa"));
        assertFalse(pattern.matches("b"));
    }
}
