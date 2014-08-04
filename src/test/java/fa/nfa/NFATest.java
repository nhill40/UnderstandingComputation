package fa.nfa;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE4;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NFATest {

    @Test
    public void test_accepting() {
        // Given some current states and a list of accept states, the NFA can tell us if we are in an accept state:
        assertFalse(new NFA(new HashSet<>(Arrays.asList(STATE1)), Arrays.asList(STATE4), NFATestRules.NFA_RULEBOOK2).accepting());
        assertTrue(new NFA(new HashSet<>(Arrays.asList(STATE1, STATE2, STATE4)), Arrays.asList(STATE4), NFATestRules.NFA_RULEBOOK2).accepting());

        // The NFA maintains current possible states and can be fed input one character at time to mutate those states.
        NFA nfa = new NFA(new HashSet<>(Arrays.asList(STATE1)), Arrays.asList(STATE4), NFATestRules.NFA_RULEBOOK2);
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertFalse(nfa.accepting());
        nfa.readCharacter('a');
        assertFalse(nfa.accepting());
        nfa.readCharacter('b');
        assertTrue(nfa.accepting());

        // For convenience, we can also pass a string - which will be broken into a char array under the covers.
        nfa = new NFA(new HashSet<>(Arrays.asList(STATE1)), Arrays.asList(STATE4), NFATestRules.NFA_RULEBOOK2);
        assertFalse(nfa.accepting());
        nfa.readString("bbbbb");
        assertTrue(nfa.accepting());
    }
}
