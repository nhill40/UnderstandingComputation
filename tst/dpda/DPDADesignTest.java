package dpda;

import fa.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static dpda.DPDATestRules.DPDARULEBOOK;
import static fa.FATestStates.STATE1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DPDADesignTest {

    @Test
    public void test_accepts() {
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);
        assertTrue(dpdaDesign.accepts("(((((((((())))))))))"));
        assertTrue(dpdaDesign.accepts("()(())((()))(()(()))"));
        assertFalse(dpdaDesign.accepts("(()(()(()()(()()))()"));
    }

    @Test
    public void test_stuck() {
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);

        // No rule applies for a closing paren with no corresponding open paren!!
        assertFalse(dpdaDesign.accepts("())"));
    }
}
