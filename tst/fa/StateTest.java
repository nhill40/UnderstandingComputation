package fa;

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

public class StateTest {

    @Test
    public void test_buildState() {
        Set<State> inputStates = new HashSet<>();
        inputStates.add(new State(1));
        inputStates.add(new State(2));
        State result = State.buildState(inputStates);
        assertEquals(2, result.getIdentifiers().size());
        assertTrue(result.getIdentifiers().containsAll(Arrays.asList(1, 2)));
    }

    @Test
    public void test_getIdentifiers() {
        Set<State> states = new HashSet<>();
        states.addAll(Arrays.asList(STATE1, STATE2, STATE3));
        Set<Integer> identifiers = State.getIdentifiers(states);
        assertEquals(3, identifiers.size());
        assertTrue(identifiers.containsAll(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void test_isSubset() {
        Set<Set<State>> potentialSuperset = new HashSet<>();
        potentialSuperset.add(new HashSet<>(Arrays.asList(STATE1, STATE2)));
        Set<Set<State>> potentialSubset = new HashSet<>();
        potentialSubset.add(new HashSet<>(Arrays.asList(STATE2, STATE3)));
        potentialSubset.add(new HashSet<>(Arrays.asList(STATE1, STATE2)));
        assertFalse(State.isSubset(potentialSuperset, potentialSubset));

        potentialSuperset = new HashSet<>();
        potentialSuperset.add(new HashSet<>(Arrays.asList(STATE1, STATE2)));
        potentialSuperset.add(new HashSet<>(Arrays.asList(STATE4, STATE5)));
        potentialSubset = new HashSet<>();
        potentialSubset.add(new HashSet<>(Arrays.asList(STATE2, STATE3)));
        potentialSubset.add(new HashSet<>(Arrays.asList(STATE1, STATE2)));
        assertFalse(State.isSubset(potentialSuperset, potentialSubset));

        potentialSuperset = new HashSet<>();
        potentialSuperset.add(new HashSet<>(Arrays.asList(STATE1, STATE2)));
        potentialSuperset.add(new HashSet<>(Arrays.asList(STATE2, STATE3)));
        potentialSubset = new HashSet<>();
        potentialSubset.add(new HashSet<>(Arrays.asList(STATE2, STATE3)));
        assertTrue(State.isSubset(potentialSuperset, potentialSubset));
    }

    @Test
    public void test_isEquivalentTo() {
        State firstState = new State(1 ,2);
        State secondState = new State(2, 3);
        State thirdState = new State(2, 1);
        assertTrue(firstState.isEquivalentTo(thirdState));
        assertFalse(firstState.isEquivalentTo(secondState));
    }
}
