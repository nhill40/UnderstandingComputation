package regex;

import fa.nfa.NFADesign;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EmptyTest {

    @Test
    public void test_toNFADesign() {
        NFADesign nfaDesign = new Empty().toNFADesign();
        assertTrue(nfaDesign.accepts(""));
        assertFalse(nfaDesign.accepts("a"));
        assertFalse(nfaDesign.accepts("aa"));
    }

    @Test
    public void test_matches(){
        // For convenience, we can ask the pattern directly - will create an J.I.T. NFADesign for us under the covers:
        assertTrue(new Empty().matches(""));
        assertFalse(new Empty().matches("a"));
        assertFalse(new Empty().matches("aa"));
    }
}
