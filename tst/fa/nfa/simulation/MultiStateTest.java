package fa.nfa.simulation;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.FATestStates.STATE5;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MultiStateTest {

    @Test
    public void test_isSubset() {
        Set<MultiState> potentialSuperset = new HashSet<>();
        potentialSuperset.add(new MultiState(STATE1, STATE2));
        Set<MultiState> potentialSubset = new HashSet<>();
        potentialSubset.add(new MultiState(STATE2, STATE3));
        potentialSubset.add(new MultiState(STATE1, STATE2));
        assertFalse(MultiState.isSubset(potentialSuperset, potentialSubset));

        potentialSuperset = new HashSet<>();
        potentialSuperset.add(new MultiState(STATE1, STATE2));
        potentialSuperset.add(new MultiState(STATE4, STATE5));
        potentialSubset = new HashSet<>();
        potentialSubset.add(new MultiState(STATE2, STATE3));
        potentialSubset.add(new MultiState(STATE1, STATE2));
        assertFalse(MultiState.isSubset(potentialSuperset, potentialSubset));

        potentialSuperset = new HashSet<>();
        potentialSuperset.add(new MultiState(STATE1, STATE2));
        potentialSuperset.add(new MultiState(STATE2, STATE3));
        potentialSubset = new HashSet<>();
        potentialSubset.add(new MultiState(STATE2, STATE3));
        assertTrue(MultiState.isSubset(potentialSuperset, potentialSubset));
    }

    @Test
    public void test_getIdentifiers() {
        MultiState states = new MultiState(STATE1, STATE2, STATE3);
        Set<Integer> identifiers = states.getIdentifiers();
        assertEquals(3, identifiers.size());
        assertTrue(identifiers.containsAll(Arrays.asList(1, 2, 3)));
    }
}
