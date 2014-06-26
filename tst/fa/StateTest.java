package fa;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
    public void test_isEquivalentTo() {
        State firstState = new State(1 ,2);
        State secondState = new State(2, 3);
        State thirdState = new State(2, 1);
        assertTrue(firstState.isEquivalentTo(thirdState));
        assertFalse(firstState.isEquivalentTo(secondState));
    }
}
