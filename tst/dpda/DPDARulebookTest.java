package dpda;

import org.junit.Test;

import java.util.Arrays;

import static dpda.DPDATestRules.DPDARULEBOOK;
import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static org.junit.Assert.assertEquals;

public class DPDARulebookTest {

    @Test
    public void test_nextConfiguration() {
        PDAConfiguration configuration = new PDAConfiguration(STATE1, new Stack(Arrays.asList('$')));
        configuration = DPDARULEBOOK.nextConfiguration(configuration, '(');
        assertEquals("state=2, stack=Stack (b)$", configuration.toString());
        configuration = DPDARULEBOOK.nextConfiguration(configuration, '(');
        assertEquals("state=2, stack=Stack (b)b$", configuration.toString());
        configuration = DPDARULEBOOK.nextConfiguration(configuration, ')');
        assertEquals("state=2, stack=Stack (b)$", configuration.toString());
    }

    @Test
    public void test_followFreeMoves() {
        PDAConfiguration configuration = new PDAConfiguration(STATE2, new Stack(Arrays.asList('$')));
        assertEquals("state=1, stack=Stack ($)", DPDARULEBOOK.followFreeMoves(configuration).toString());
    }

    @Test(expected = StackOverflowError.class)
    public void test_followFreeMoves_resultsInExpectedInfiniteLoop() {
        DPDARulebook rulebook = new DPDARulebook(Arrays.asList(
                new PDARule(STATE1, null, STATE1, '$', Arrays.asList('$'))
        ));
        rulebook.followFreeMoves(new PDAConfiguration(STATE1, new Stack(Arrays.asList('$'))));
    }
}
