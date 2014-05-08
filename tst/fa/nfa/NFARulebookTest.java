package fa.nfa;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NFARulebookTest {

    @Test
    public void test_alphabet() {
        Set<Character> alphabet = NFATestRules.NFA_RULEBOOK.alphabet();
        assertEquals(2, alphabet.size());
        assertTrue(alphabet.containsAll(Arrays.asList('a', 'b')));
    }
}
