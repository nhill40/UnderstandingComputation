package fa;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StateTest {

    @Test
    public void test_buildState() {
        Set<State> inputStates = new HashSet<State>();
        inputStates.add(new State(1));
        inputStates.add(new State(2));
        State result = State.buildState(inputStates);
        assertEquals(2, result.getIdentifiers().size());
        assertTrue(result.getIdentifiers().containsAll(Arrays.asList(1, 2)));
    }
}
