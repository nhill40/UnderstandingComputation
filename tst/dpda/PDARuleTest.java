package dpda;

import org.junit.Test;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class PDARuleTest {

    @Test
    public void test_appliesTo() {
        PDARule rule = new PDARule(STATE1, '(', STATE2, '$', Arrays.asList('b', '$'));
        PDAConfiguration configuration = new PDAConfiguration(STATE1, new Stack(Arrays.asList('$')));
        assertTrue(rule.appliesTo(configuration, '('));
    }

    @Test
    public void test_follow() {
        PDARule rule = new PDARule(STATE1, '(', STATE2, '$', Arrays.asList('b', '$'));
        PDAConfiguration configuration = new PDAConfiguration(STATE1, new Stack(Arrays.asList('$')));
        assertEquals("state=2, stack=Stack (b)$", rule.follow(configuration).toString());
    }
}
