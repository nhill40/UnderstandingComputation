package fa.dfa;

import fa.FARule;
import fa.FASingleRule;
import fa.SingleState;
import fa.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE3;
import static fa.dfa.DFATestRules.DFA_RULEBOOOK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DFADesignTest {

    @Test
    public void test_accepts() {
        Set<State> acceptStates = new HashSet<>();
        // TODO: look for other instances of this copy/paste nonsense:
        acceptStates.addAll(Arrays.asList(STATE3));
        DFADesign dfaDesign = new DFADesign(STATE1, acceptStates, DFA_RULEBOOOK);
        assertTrue(dfaDesign.accepts("baba"));
        assertFalse(dfaDesign.accepts("baa"));
    }

    /**
     * Equivalent to same-named test found in NFADesignTest.
     */
    @Test
    public void test_NFAtoDFAConversion_baseline() {
        final State STATE_1_OR_2 = new SingleState(1, 2);
        final State STATE_2_OR_3 = new SingleState(2, 3);
        final State STATE_NONE = new SingleState(null);
        final State STATE_1_2_OR_3 = new SingleState(1, 2, 3);

        List<FARule> rules = new ArrayList<>();
        rules.addAll(Arrays.asList(
                new FASingleRule(STATE_1_OR_2, 'a', STATE_1_OR_2), new FASingleRule(STATE_1_OR_2, 'b', STATE_2_OR_3),
                new FASingleRule(STATE_2_OR_3, 'a', STATE_NONE), new FASingleRule(STATE_2_OR_3, 'b', STATE_1_2_OR_3),
                new FASingleRule(STATE_NONE, 'a', STATE_NONE), new FASingleRule(STATE_NONE, 'b', STATE_NONE),
                new FASingleRule(STATE_1_2_OR_3, 'b', STATE_1_2_OR_3), new FASingleRule(STATE_1_2_OR_3, 'a', STATE_1_OR_2)
        ));
        DFARulebook rulebook = new DFARulebook(rules);

        Set<State> acceptStates = new HashSet<>();
        acceptStates.addAll(Arrays.asList(STATE_2_OR_3, STATE_1_2_OR_3));
        DFADesign dfaDesign = new DFADesign(STATE_1_OR_2, acceptStates, rulebook);
        assertFalse(dfaDesign.accepts("aaa"));
        assertTrue(dfaDesign.accepts("aab"));
        assertTrue(dfaDesign.accepts("bbbabb"));
    }
}
