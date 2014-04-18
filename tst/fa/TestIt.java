package fa;

import fa.dfa.DFA;
import fa.dfa.DFADesign;
import fa.dfa.DFARulebook;
import fa.nfa.NFA;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class TestIt {

    private final State state1 = new State();
    private final State state2 = new State();
    private final State state3 = new State();
    private final State state4 = new State();
    private final State state5 = new State();
    private final State state6 = new State();

    @Test
    public void test_DFA() {


        // A set of rules depicting a DFA that accepts strings containing the sequence 'ab'
        DFARulebook rulebook = new DFARulebook(Arrays.asList(
                new FARule(state1, 'a', state2), new FARule(state1, 'b', state1),
                new FARule(state2, 'a', state2), new FARule(state2, 'b', state3),
                new FARule(state3, 'a', state3), new FARule(state3, 'b', state3)));

        // Given a current state & an input, our DFA rulebook can definitively tell you what state to move to next:
        assertEquals(state2, rulebook.nextState(state1, 'a'));
        assertEquals(state3, rulebook.nextState(state2, 'b'));

        // Given a current state and a list of accept states, the DFA can tell us if we are in an accept state:
        assertTrue(new DFA(state1, Arrays.asList(state1, state3), rulebook).accepting());
        assertFalse(new DFA(state1, Arrays.asList(state3), rulebook).accepting());

        // The dfa maintains current state & can be fed input one character at a time to mutate that state:
        DFA dfa = new DFA(state1, Arrays.asList(state3), rulebook);
        assertFalse(dfa.accepting());
        dfa.readCharacter('b');
        assertFalse(dfa.accepting());
        dfa.readCharacter('a');
        dfa.readCharacter('a');
        dfa.readCharacter('a');
        assertFalse(dfa.accepting());
        dfa.readCharacter('b');
        assertTrue(dfa.accepting());
        // Conclusion: according to this DFA, "baaab" is an accepted string of characters.

        // For convenience, we can also pass in a string (which will be converted to a char array under the covers that
        // the DFA will loop thru)
        dfa = new DFA(state1, Arrays.asList(state3), rulebook);
        assertFalse(dfa.accepting());
        dfa.readString("baaab");
        assertTrue(dfa.accepting());

        DFADesign dfaDesign = new DFADesign(state1, Arrays.asList(state3), rulebook);
        assertTrue(dfaDesign.accepts("baba"));
        assertFalse(dfaDesign.accepts("baa"));
    }

    @Test
    public void test_NFA() {

        // a set of rules depicting a NFA that accepts strings where the 3rd to last character is 'b'
        NFARulebook rulebook = new NFARulebook(Arrays.asList(
                new FARule(state1, 'a', state1), new FARule(state1, 'b', state1), new FARule(state1, 'b', state2),
                new FARule(state2, 'a', state3), new FARule(state2, 'b', state3),
                new FARule(state3, 'a', state4), new FARule(state3, 'b', state4)));

        // Given some current states & an input, our DFA rulebook can definitively tell you what states to move to next:
        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(state1)), 'b');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(state1, state2)));

        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(state1, state2)), 'a');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(state1, state3)));

        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(state1, state3)), 'b');
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(state1, state2, state4)));

        // Given some current states and a list of accept states, the NFA can tell us if we are in an accept state:
        assertFalse(new NFA(new HashSet<State>(Arrays.asList(state1)), Arrays.asList(state4), rulebook).accepting());
        assertTrue(new NFA(new HashSet<State>(Arrays.asList(state1, state2, state4)), Arrays.asList(state4), rulebook).accepting());

        // The NFA maintains current possible states and can be fed input one character at time to mutate those states.
        NFA nfa = new NFA(new HashSet<State>(Arrays.asList(state1)), Arrays.asList(state4), rulebook);
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertFalse(nfa.accepting());
        nfa.readCharacter('a');
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertTrue(nfa.accepting());

        // For convenience, we can also pass a sting - which will be broken into a char array under the covers.
        nfa = new NFA(new HashSet<State>(Arrays.asList(state1)), Arrays.asList(state4), rulebook);
        assertFalse(nfa.accepting());
        nfa.readString("bbbbb");
        assertTrue(nfa.accepting());

        NFADesign nfaDesign = new NFADesign(state1, Arrays.asList(state4), rulebook);
        assertTrue(nfaDesign.accepts("bab"));
        assertFalse(nfaDesign.accepts("baaaaaaaaaaaaaaaaa"));
        assertTrue(nfaDesign.accepts("baaaaaaaaaaaaaabaa"));
    }

    @Test
    public void test_NFAWithFreeMoves() {

        // a set of rules depicting a NFA that accepts strings of a length that is a multiple of 2 OR 3.
        // Note that this requires the use of "free moves" - represented by nulls ('\0' is char equivalent of null).
        NFARulebook rulebook = new NFARulebook(Arrays.asList(
                new FARule(state1, '\0', state2), new FARule(state1, '\0', state4),
                new FARule(state2, 'a', state3),
                new FARule(state3, 'a', state2),
                new FARule(state4, 'a', state5),
                new FARule(state5, 'a', state6),
                new FARule(state6, 'a', state4)));

        Set<State> possibleNextStates;
        possibleNextStates = rulebook.nextStates(new HashSet<State>(Arrays.asList(state1)), '\0');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(state2, state4)));

        possibleNextStates = rulebook.followFreeMoves(new HashSet<State>(Arrays.asList(state1)));
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(state1, state2, state4)));

        NFADesign nfaDesign = new NFADesign(state1, Arrays.asList(state2, state4), rulebook);
        assertTrue(nfaDesign.accepts("aa"));
        assertTrue(nfaDesign.accepts("aaa"));
        assertFalse(nfaDesign.accepts("aaaaa"));
        assertTrue(nfaDesign.accepts("aaaaaa"));
    }
}
