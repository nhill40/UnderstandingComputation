package fa;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SingleStateTest {

    @Test
    public void test_buildState() {
        Set<SingleState> inputStates = new HashSet<>();
        inputStates.add(new SingleState(1));
        inputStates.add(new SingleState(2));
        SingleState result = SingleState.buildState(inputStates);
        assertEquals(2, result.getIdentifiers().size());
        assertTrue(result.getIdentifiers().containsAll(Arrays.asList(1, 2)));
    }

    @Test
    public void test_isEquivalentTo() {
        SingleState firstState = new SingleState(1 ,2);
        SingleState secondState = new SingleState(2, 3);
        SingleState thirdState = new SingleState(2, 1);
        assertTrue(firstState.isEquivalentTo(thirdState));
        assertFalse(firstState.isEquivalentTo(secondState));
    }
}
