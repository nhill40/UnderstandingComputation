package regex;

import fa.nfa.NFADesign;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestIt {

    @Test
    public void test() {
        Pattern pattern = new Repeat(new Choose(new Concatenate(new Literal('a'), new Literal('b')), new Literal('a')));
        assertEquals("/(ab|a)*/", pattern.toRegEx());

        NFADesign nfaDesign = new Empty().toNFADesign();
        assertTrue(nfaDesign.accepts(""));
        assertFalse(nfaDesign.accepts("a"));
        assertFalse(nfaDesign.accepts("aa"));
        // For convenience, we can ask the pattern directly:
        assertTrue(new Empty().matches(""));
        assertFalse(new Empty().matches("a"));
        assertFalse(new Empty().matches("aa"));

        nfaDesign = new Literal('a').toNFADesign();
        assertFalse(nfaDesign.accepts(""));
        assertTrue(nfaDesign.accepts("a"));
        assertFalse(nfaDesign.accepts("b"));
        assertTrue(nfaDesign.accepts("aa"));
        // For convenience, we can ask the pattern directly:
        assertFalse(new Literal('a').matches(""));
        assertTrue(new Literal('a').matches("a"));
        assertFalse(new Literal('a').matches("b"));
        assertTrue(new Literal('a').matches("aa"));

        pattern = new Concatenate(new Literal('a'), new Literal('b'));
        assertEquals("/ab/", pattern.toRegEx());
        assertFalse(pattern.matches("a"));
        assertTrue(pattern.matches("ab"));
        assertFalse(pattern.matches("abc"));

        pattern = new Concatenate(new Literal('a'), new Concatenate(new Literal('b'), new Literal('c')));
        assertEquals("/abc/", pattern.toRegEx());
        assertFalse(pattern.matches("a"));
        assertFalse(pattern.matches("ab"));
        assertTrue(pattern.matches("abc"));
    }
}
