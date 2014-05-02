package regex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ChooseTest {

    @Test
    public void test_matches() {
        Pattern pattern = new Choose(new Literal('a'), new Literal('b'));
        assertEquals("/a|b/", pattern.toRegEx());
        assertTrue(pattern.matches("a"));
        assertTrue(pattern.matches("b"));
        assertFalse(pattern.matches("c"));
    }

    @Test
    public void test_matchesWithEmptyString() {
        Pattern pattern = new Choose(new Empty(), new Literal('b'));
        assertEquals("/|b/", pattern.toRegEx());
        assertTrue(pattern.matches(""));
        assertFalse(pattern.matches("a"));
        assertTrue(pattern.matches("b"));
    }
}
