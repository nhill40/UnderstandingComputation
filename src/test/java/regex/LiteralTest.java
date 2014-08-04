package regex;

import fa.nfa.NFADesign;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LiteralTest {

    @Test
    public void test_toNFADesign() {
        NFADesign nfaDesign = new Literal('a').toNFADesign();
        assertFalse(nfaDesign.accepts(""));
        assertTrue(nfaDesign.accepts("a"));
        assertFalse(nfaDesign.accepts("b"));
        assertFalse(nfaDesign.accepts("aa"));
    }

    @Test
    public void test_matches() {
        // For convenience, we can ask the pattern directly:
        assertFalse(new Literal('a').matches(""));
        assertTrue(new Literal('a').matches("a"));
        assertFalse(new Literal('a').matches("b"));
        assertFalse(new Literal('a').matches("aa"));
    }
}
