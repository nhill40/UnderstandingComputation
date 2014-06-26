package fa.nfa.simulation;

import org.junit.Test;

import java.util.Arrays;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MultiStateTest {

    @Test
    public void test_getIdentifiers() {
        MultiState states = new MultiState(STATE1, STATE2, STATE3);
        Set<Integer> identifiers = states.getIdentifiers();
        assertEquals(3, identifiers.size());
        assertTrue(identifiers.containsAll(Arrays.asList(1, 2, 3)));
    }
}
