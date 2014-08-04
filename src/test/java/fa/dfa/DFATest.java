package fa.dfa;

import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE3;
import static fa.dfa.DFATestRules.DFA_RULEBOOOK;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DFATest {

    @Test
    public void test_accepting() {
        // Given a current state and a list of accept states, the DFA can tell us if we are in an accept state:
        Set<State> acceptStates = new HashSet<>();
        acceptStates.addAll(Arrays.asList(STATE1, STATE3));
        assertTrue(new DFA(STATE1, acceptStates, DFA_RULEBOOOK).accepting());

        acceptStates = new HashSet<>();
        acceptStates.add(STATE3);
        assertFalse(new DFA(STATE1, acceptStates, DFA_RULEBOOOK).accepting());

        // The dfa maintains current state & can be fed input one character at a time to mutate that state:
        acceptStates = new HashSet<>();
        acceptStates.add(STATE3);
        DFA dfa = new DFA(STATE1, acceptStates, DFA_RULEBOOOK);
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
        acceptStates = new HashSet<>();
        acceptStates.add(STATE3);
        dfa = new DFA(STATE1, acceptStates, DFA_RULEBOOOK);
        assertFalse(dfa.accepting());
        dfa.readString("baaab");
        assertTrue(dfa.accepting());
    }
}
