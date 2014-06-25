package fa.dfa;

import fa.FARule;
import fa.State;
import org.junit.Test;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE3;
import static fa.dfa.DFATestRules.DFA_RULEBOOOK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DFADesignTest {

    @Test
    public void test_accepts() {
        DFADesign dfaDesign = new DFADesign(STATE1, Arrays.asList(STATE3), DFA_RULEBOOOK);
        assertTrue(dfaDesign.accepts("baba"));
        assertFalse(dfaDesign.accepts("baa"));
    }

    /**
     * Equivalent to same-named test found in NFADesignTest.
     */
    @Test
    public void test_NFAtoDFAConversion_baseline() {
        final State STATE_1_OR_2 = new State(1, 2);
        final State STATE_2_OR_3 = new State(2, 3);
        final State STATE_NONE = new State(null);
        final State STATE_1_2_OR_3 = new State(1, 2, 3);

        DFARulebook rulebook = new DFARulebook(Arrays.asList(
                new FARule(STATE_1_OR_2, 'a', STATE_1_OR_2), new FARule(STATE_1_OR_2, 'b', STATE_2_OR_3),
                new FARule(STATE_2_OR_3, 'a', STATE_NONE), new FARule(STATE_2_OR_3, 'b', STATE_1_2_OR_3),
                new FARule(STATE_NONE, 'a', STATE_NONE), new FARule(STATE_NONE, 'b', STATE_NONE),
                new FARule(STATE_1_2_OR_3, 'b', STATE_1_2_OR_3), new FARule(STATE_1_2_OR_3, 'a', STATE_1_OR_2)
        ));

        DFADesign dfaDesign = new DFADesign(STATE_1_OR_2, Arrays.asList(STATE_2_OR_3, STATE_1_2_OR_3), rulebook);
        assertFalse(dfaDesign.accepts("aaa"));
        assertTrue(dfaDesign.accepts("aab"));
        assertTrue(dfaDesign.accepts("bbbabb"));
    }
}
