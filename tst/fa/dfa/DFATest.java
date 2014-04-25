package fa.dfa;

import org.junit.Test;

import java.util.Arrays;

import static fa.dfa.DFATestRules.DFA_RULEBOOOK;
import static fa.FATestStates.*;
import static org.junit.Assert.*;

public class DFATest {

    @Test
    public void test_DFA() {
        // Given a current state & an input, our DFA rulebook can definitively tell you what state to move to next:
        assertEquals(STATE2, DFA_RULEBOOOK.nextState(STATE1, 'a'));
        assertEquals(STATE3, DFA_RULEBOOOK.nextState(STATE2, 'b'));

        // Given a current state and a list of accept states, the DFA can tell us if we are in an accept state:
        assertTrue(new DFA(STATE1, Arrays.asList(STATE1, STATE3), DFA_RULEBOOOK).accepting());
        assertFalse(new DFA(STATE1, Arrays.asList(STATE3), DFA_RULEBOOOK).accepting());

        // The dfa maintains current state & can be fed input one character at a time to mutate that state:
        DFA dfa = new DFA(STATE1, Arrays.asList(STATE3), DFA_RULEBOOOK);
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
        dfa = new DFA(STATE1, Arrays.asList(STATE3), DFA_RULEBOOOK);
        assertFalse(dfa.accepting());
        dfa.readString("baaab");
        assertTrue(dfa.accepting());

        DFADesign dfaDesign = new DFADesign(STATE1, Arrays.asList(STATE3), DFA_RULEBOOOK);
        assertTrue(dfaDesign.accepts("baba"));
        assertFalse(dfaDesign.accepts("baa"));
    }
}
