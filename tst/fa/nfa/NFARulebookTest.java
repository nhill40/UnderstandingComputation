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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NFARulebookTest {

    @Test
    public void test_alphabet() {
        Set<Character> alphabet = NFATestRules.NFA_RULEBOOK.alphabet();
        assertEquals(2, alphabet.size());
        assertTrue(alphabet.containsAll(Arrays.asList('a', 'b')));
    }

    @Test
    public void test_nextStates() {
        // Given some current states & an input, our DFA rulebook can definitively tell you what states to move to next:
        Set<State> possibleNextStates;
        possibleNextStates = NFATestRules.NFA_RULEBOOK2.nextStates(new HashSet<>(Arrays.asList(STATE1)), 'b');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2)));

        possibleNextStates = NFATestRules.NFA_RULEBOOK2.nextStates(new HashSet<>(Arrays.asList(STATE1, STATE2)), 'a');
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE3)));

        possibleNextStates = NFATestRules.NFA_RULEBOOK2.nextStates(new HashSet<>(Arrays.asList(STATE1, STATE3)), 'b');
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));
    }

    @Test
    public void test_nextStatesWithFreeMoves() {
        Set<State> possibleNextStates;
        possibleNextStates = NFATestRules.NFA_RULEBOOK3.nextStates(new HashSet<>(Arrays.asList(STATE1)), null);
        assertEquals(2, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE2, STATE4)));

        possibleNextStates = NFATestRules.NFA_RULEBOOK3.followFreeMoves(new HashSet<>(Arrays.asList(STATE1)));
        assertEquals(3, possibleNextStates.size());
        assertTrue(possibleNextStates.containsAll(Arrays.asList(STATE1, STATE2, STATE4)));
    }
}
