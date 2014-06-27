package fa.dfa;

import fa.FARule;
import fa.State;
import fa.dfa.alternate.DFADesignAlt;
import fa.dfa.alternate.DFARulebookAlt;
import fa.nfa.simulation.FAMultiRule;
import fa.nfa.simulation.MultiState;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
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

    @Test
    public void test_NFAtoDFAConversion_altImplementation_baseline() {
        final MultiState STATE_1_OR_2 = new MultiState(STATE1, STATE2);
        final MultiState STATE_2_OR_3 = new MultiState(STATE2, STATE3);
        final MultiState STATE_NONE = new MultiState(null);
        final MultiState STATE_1_2_OR_3 = new MultiState(STATE1, STATE2, STATE3);

        DFARulebookAlt rulebook = new DFARulebookAlt(Arrays.asList(
                new FAMultiRule(STATE_1_OR_2, 'a', STATE_1_OR_2), new FAMultiRule(STATE_1_OR_2, 'b', STATE_2_OR_3),
                new FAMultiRule(STATE_2_OR_3, 'a', STATE_NONE), new FAMultiRule(STATE_2_OR_3, 'b', STATE_1_2_OR_3),
                new FAMultiRule(STATE_NONE, 'a', STATE_NONE), new FAMultiRule(STATE_NONE, 'b', STATE_NONE),
                new FAMultiRule(STATE_1_2_OR_3, 'b', STATE_1_2_OR_3), new FAMultiRule(STATE_1_2_OR_3, 'a', STATE_1_OR_2)
        ));

        DFADesignAlt dfaDesign = new DFADesignAlt(STATE_1_OR_2, new HashSet<>(Arrays.asList(STATE_2_OR_3, STATE_1_2_OR_3)), rulebook);
        assertFalse(dfaDesign.accepts("aaa"));
        assertTrue(dfaDesign.accepts("aab"));
        assertTrue(dfaDesign.accepts("bbbabb"));
    }
}
