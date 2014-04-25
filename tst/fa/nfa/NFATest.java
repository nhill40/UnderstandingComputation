package fa.nfa;

import fa.FARule;
import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.FATestStates.STATE5;
import static fa.FATestStates.STATE6;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NFATest {

    @Test
    public void test_NFA() {

        // a set of rules depicting a NFA that accepts strings where the 3rd to last character is 'b'
        NFARulebook rulebook = new NFARulebook(Arrays.asList(
                new FARule(STATE1, 'a', STATE1), new FARule(STATE1, 'b', STATE1), new FARule(STATE1, 'b', STATE2),
                new FARule(STATE2, 'a', STATE3), new FARule(STATE2, 'b', STATE3),
                new FARule(STATE3, 'a', STATE4), new FARule(STATE3, 'b', STATE4)));

        // Given some current states & an input, our DFA rulebook can definitively tell you what states to move to next:
        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(STATE1)), 'b');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2)));

        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(STATE1, STATE2)), 'a');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE3)));

        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(STATE1, STATE3)), 'b');
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));

        // Given some current states and a list of accept states, the NFA can tell us if we are in an accept state:
        assertFalse(new NFA(new HashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook).accepting());
        assertTrue(new NFA(new HashSet<State>(Arrays.asList(STATE1, STATE2, STATE4)), Arrays.asList(STATE4), rulebook).accepting());

        // The NFA maintains current possible states and can be fed input one character at time to mutate those states.
        NFA nfa = new NFA(new HashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook);
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertFalse(nfa.accepting());
        nfa.readCharacter('a');
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertTrue(nfa.accepting());

        // For convenience, we can also pass a sting - which will be broken into a char array under the covers.
        nfa = new NFA(new HashSet<State>(Arrays.asList(STATE1)), Arrays.asList(STATE4), rulebook);
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
                new FARule(STATE1, null, STATE2), new FARule(STATE1, null, STATE4),
                new FARule(STATE2, 'a', STATE3),
                new FARule(STATE3, 'a', STATE2),
                new FARule(STATE4, 'a', STATE5),
                new FARule(STATE5, 'a', STATE6),
                new FARule(STATE6, 'a', STATE4)));

        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(STATE1)), null);
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE2, STATE4)));

        possibleNextStates = rulebook.followFreeMoves(new HashSet<State>(Arrays.asList(STATE1)));
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));

        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE2, STATE4), rulebook);
        assertTrue(nfaDesign.accepts("aa"));
        assertTrue(nfaDesign.accepts("aaa"));
        assertFalse(nfaDesign.accepts("aaaaa"));
        assertTrue(nfaDesign.accepts("aaaaaa"));
    }
}
