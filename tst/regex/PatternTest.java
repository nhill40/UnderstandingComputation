package regex;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatternTest {

    @Test
    public void test_toRegEx() {
        Pattern pattern = new Repeat(new Choose(new Concatenate(new Literal('a'), new Literal('b')), new Literal('a')));
        assertEquals("/(ab|a)*/", pattern.toRegEx());
    }

    @Test
    public void test_compoundPatterns() {
        Pattern pattern = new Repeat(
                new Concatenate(
                        new Literal('a'),
                        new Choose(new Empty(), new Literal('b'))));
        assertEquals("/(a(|b))*/", pattern.toRegEx());
        assertTrue(pattern.matches(""));
        assertTrue(pattern.matches("a"));
        assertTrue(pattern.matches("ab"));
        assertTrue(pattern.matches("aba"));
        assertTrue(pattern.matches("abab"));
        assertTrue(pattern.matches("abaab"));
        assertFalse(pattern.matches("abba"));
    }
}
