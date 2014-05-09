package fa.nfa;

import fa.FASingleRule;
import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.FATestStates.STATE5;
import static fa.FATestStates.STATE6;
import static fa.nfa.NFATestRules.NFA_RULEBOOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NFATest {

    @Test
    public void test_NFA() {

        // a set of rules depicting a NFA that accepts strings where the 3rd to last character is 'b'
        NFARulebook rulebook = new NFARulebook(Arrays.asList(
                new FASingleRule(STATE1, 'a', STATE1), new FASingleRule(STATE1, 'b', STATE1), new FASingleRule(STATE1, 'b', STATE2),
                new FASingleRule(STATE2, 'a', STATE3), new FASingleRule(STATE2, 'b', STATE3),
                new FASingleRule(STATE3, 'a', STATE4), new FASingleRule(STATE3, 'b', STATE4)));

        // Given some current states & an input, our DFA rulebook can definitively tell you what states to move to next:
        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1)), 'b');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2)));

        possibleNextStates = rulebook.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2)), 'a');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE3)));

        possibleNextStates = rulebook.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE3)), 'b');
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));

        // Given some current states and a list of accept states, the NFA can tell us if we are in an accept state:
        assertFalse(new NFA(new LinkedHashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook).accepting());
        assertTrue(new NFA(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2, STATE4)), Arrays.asList(STATE4), rulebook).accepting());

        // The NFA maintains current possible states and can be fed input one character at time to mutate those states.
        NFA nfa = new NFA(new LinkedHashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook);
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertFalse(nfa.accepting());
        nfa.readCharacter('a');
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertTrue(nfa.accepting());

        // For convenience, we can also pass a sting - which will be broken into a char array under the covers.
        nfa = new NFA(new LinkedHashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook);
        assertFalse(nfa.accepting());
        nfa.readString("bbbbb");
        assertTrue(nfa.accepting());

        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE4), rulebook);
        assertTrue(nfaDesign.accepts("bab"));
        assertFalse(nfaDesign.accepts("baaaaaaaaaaaaaaaaa"));
        assertTrue(nfaDesign.accepts("baaaaaaaaaaaaaabaa"));
    }

    @Test
    public void test_NFAWithFreeMoves() {

        // a set of rules depicting a NFA that accepts strings of a length that is a multiple of 2 OR 3.
        // Note that this requires the use of "free moves" - represented by nulls ('\0' is char equivalent of null).
        NFARulebook rulebook = new NFARulebook(Arrays.asList(
                new FASingleRule(STATE1, null, STATE2), new FASingleRule(STATE1, null, STATE4),
                new FASingleRule(STATE2, 'a', STATE3),
                new FASingleRule(STATE3, 'a', STATE2),
                new FASingleRule(STATE4, 'a', STATE5),
                new FASingleRule(STATE5, 'a', STATE6),
                new FASingleRule(STATE6, 'a', STATE4)));

        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1)), null);
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE2, STATE4)));

        possibleNextStates = rulebook.followFreeMoves(new LinkedHashSet<State>(Arrays.asList(STATE1)));
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));

        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE2, STATE4), rulebook);
        assertTrue(nfaDesign.accepts("aa"));
        assertTrue(nfaDesign.accepts("aaa"));
        assertFalse(nfaDesign.accepts("aaaaa"));
        assertTrue(nfaDesign.accepts("aaaaaa"));
    }

    /**
     * Equivalent to same-named test found in DFATest.
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

        NFA nfa = nfaDesign.toNFA(new LinkedHashSet<State>(Arrays.asList(STATE2, STATE3)));
        nfa.readCharacter('b');
        currentStatesConsideringFreeMoves = nfa.getCurrentStatesConsideringFreeMoves();
        assertEquals(3, currentStatesConsideringFreeMoves.size());
        assertTrue(currentStatesConsideringFreeMoves.containsAll(Arrays.asList(STATE1, STATE2, STATE3)));
        // Refer to NFASimulationTest to see this functionality automated
    }
}
