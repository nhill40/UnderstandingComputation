package fa.dfa;

import org.junit.Test;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.dfa.DFATestRules.DFA_RULEBOOOK;
import static org.junit.Assert.assertEquals;

public class DFARulebookTest {

    @Test
    public void test_nextState() {
        // Given a current state & an input, our DFA rulebook can definitively tell you what state to move to next:
        assertEquals(STATE2, DFA_RULEBOOOK.nextState(STATE1, 'a'));
        assertEquals(STATE3, DFA_RULEBOOOK.nextState(STATE2, 'b'));
    }
}
