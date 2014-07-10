package dpda;

import fa.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static dpda.DPDATestRules.DPDARULEBOOK;
import static fa.FATestStates.STATE1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DPDATest {

    @Test
    public void test_accepting() {
        DPDA dpda = new DPDA(new PDAConfiguration(STATE1, new Stack(Arrays.asList('$'))),
                new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);

        assertTrue(dpda.accepting());
        dpda.readString("(()");
        assertFalse(dpda.accepting());
        assertEquals("state=2, stack=Stack (b)$", dpda.getCurrentConfiguration().toString());
    }

    @Test
    public void test_readString() {
        DPDA dpda = new DPDA(new PDAConfiguration(STATE1, new Stack(Arrays.asList('$'))),
                new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);

        dpda.readString("(()(");
        assertFalse(dpda.accepting());
        assertEquals("state=2, stack=Stack (b)b$", dpda.getCurrentConfiguration().toString());

        dpda.readString("))()");
        assertTrue(dpda.accepting());
        assertEquals("state=1, stack=Stack ($)", dpda.getCurrentConfiguration().toString());
    }

    @Test
    public void test_stuck() {
        DPDA dpda = new DPDA(new PDAConfiguration(STATE1, new Stack(Arrays.asList('$'))),
                new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);

        dpda.readString("())");
        assertEquals("state=Stuck, stack=Stack ($)", dpda.getCurrentConfiguration().toString());
        assertFalse(dpda.accepting());
        assertTrue(dpda.isStuck());
    }
}
