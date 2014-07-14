package pda.dpda;

import org.junit.Test;
import pda.PDAConfiguration;
import pda.PDARule;
import pda.Stack;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static org.junit.Assert.assertEquals;
import static pda.dpda.DPDATestRules.DPDARULEBOOK;

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
