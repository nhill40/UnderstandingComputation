package regex;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class ConcatenateTest {

    @Test
    public void test_matches() {
        Pattern pattern = new Concatenate(new Literal('a'), new Literal('b'));
        assertEquals("/ab/", pattern.toRegEx());
        assertFalse(pattern.matches("a"));
        assertTrue(pattern.matches("ab"));
        assertFalse(pattern.matches("abc"));
    }

    @Test
    public void test_compoundMatches() {
        Pattern pattern = new Concatenate(new Literal('a'), new Concatenate(new Literal('b'), new Literal('c')));
        assertEquals("/abc/", pattern.toRegEx());
        assertFalse(pattern.matches("a"));
        assertFalse(pattern.matches("ab"));
        assertTrue(pattern.matches("abc"));
    }
}
