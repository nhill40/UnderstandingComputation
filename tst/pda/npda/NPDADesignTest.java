package pda.npda;

import fa.State;
import org.junit.Test;
import pda.PDARule;

import java.util.ArrayList;
import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NPDADesignTest {

    @Test
    public void test_accepts() {
        NPDARulebook rulebook = new NPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE1, '$', Arrays.asList('a', '$')),
                new PDARule(STATE1, 'a', STATE1, 'a', Arrays.asList('a', 'a')),
                new PDARule(STATE1, 'a', STATE1, 'b', Arrays.asList('a', 'b')),
                new PDARule(STATE1, 'b', STATE1, '$', Arrays.asList('b', '$')),
                new PDARule(STATE1, 'b', STATE1, 'a', Arrays.asList('b', 'a')),
                new PDARule(STATE1, 'b', STATE1, 'b', Arrays.asList('b', 'b')),
                new PDARule(STATE1, null, STATE2, '$', Arrays.asList('$')),
                new PDARule(STATE1, null, STATE2, 'a', Arrays.asList('a')),
                new PDARule(STATE1, null, STATE2, 'b', Arrays.asList('b')),
                new PDARule(STATE2, 'a', STATE2, 'a', new ArrayList<Character>()),
                new PDARule(STATE2, 'b', STATE2, 'b', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE3, '$', Arrays.asList('$'))
        ));
        NPDADesign npdaDesign = new NPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        assertTrue(npdaDesign.accepts("abba"));
        assertTrue(npdaDesign.accepts("babbaabbab"));
        assertFalse(npdaDesign.accepts("abb"));
        assertFalse(npdaDesign.accepts("baabaa"));
    }
}
