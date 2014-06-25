package fa.nfa;

import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.nfa.NFATestRules.NFA_RULEBOOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NFADesignTest {

    @Test
    public void test_accepts() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE4), NFATestRules.NFA_RULEBOOK2);
        assertTrue(nfaDesign.accepts("bab"));
        assertFalse(nfaDesign.accepts("baaaaaaaaaaaaaaaaa"));
        assertTrue(nfaDesign.accepts("baaaaaaaaaaaaaabaa"));
    }

    @Test
    public void test_acceptsWithFreeMoves() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE2, STATE4), NFATestRules.NFA_RULEBOOK3);
        assertTrue(nfaDesign.accepts("aa"));
        assertTrue(nfaDesign.accepts("aaa"));
        assertFalse(nfaDesign.accepts("aaaaa"));
        assertTrue(nfaDesign.accepts("aaaaaa"));
    }

    /**
     * Equivalent to same-named test found in DFADesignTest.
     */
    @Test
    public void test_NFAtoDFAConversion_baseline() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        assertFalse(nfaDesign.accepts("aaa"));
        assertTrue(nfaDesign.accepts("aab"));
        assertTrue(nfaDesign.accepts("bbbabb"));
    }

    @Test
    public void test_NFASpecifyingCurrentStates() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);

        // Assert existing functionality - convert an NFADesign into a J.I.T. NFA and ask it what it's current states
        // are, taking possible free moves into account.
        Set<State> currentStatesConsideringFreeMoves = nfaDesign.toNFA().getCurrentStatesConsideringFreeMoves();
        assertEquals(2, currentStatesConsideringFreeMoves.size());
        assertTrue(currentStatesConsideringFreeMoves.containsAll(Arrays.asList(STATE1, STATE2)));

        // Now throw a wrinkle at it - we're now able to convert an NFADesign into a J.I.T. NFA with the added
        // functionality of specifying the NFA's currentStates (which previously defaulted to the start shape).  We can
        // now simulate an NFA starting from any of its valid states (i.e. not just the start state).
        currentStatesConsideringFreeMoves =
                nfaDesign.toNFA(STATE2).getCurrentStatesConsideringFreeMoves();
        assertEquals(1, currentStatesConsideringFreeMoves.size());
        assertTrue(currentStatesConsideringFreeMoves.contains(STATE2));

        currentStatesConsideringFreeMoves =
                nfaDesign.toNFA(STATE3).getCurrentStatesConsideringFreeMoves();
        assertEquals(2, currentStatesConsideringFreeMoves.size());
        assertTrue(currentStatesConsideringFreeMoves.containsAll(Arrays.asList(STATE2, STATE3)));

        NFA nfa = nfaDesign.toNFA(new HashSet<>(Arrays.asList(STATE2, STATE3)));
        nfa.readCharacter('b');
        currentStatesConsideringFreeMoves = nfa.getCurrentStatesConsideringFreeMoves();
        assertEquals(3, currentStatesConsideringFreeMoves.size());
        assertTrue(currentStatesConsideringFreeMoves.containsAll(Arrays.asList(STATE1, STATE2, STATE3)));
        // Refer to NFASimulationTest to see this functionality automated
    }
}
