package fa.nfa;

import fa.FARule;
import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.nfa.NFATestRules.NFA_RULEBOOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class NFASimulationTest {

    @Test
    public void test_nextStates() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        Set<State> currentStates;
        currentStates = simulation.nextStates(new HashSet<State>(Arrays.asList(STATE1, STATE2)), 'a');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2)));

        currentStates = simulation.nextStates(new HashSet<State>(Arrays.asList(STATE1, STATE2)), 'b');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE2, STATE3)));

        currentStates = simulation.nextStates(new HashSet<State>(Arrays.asList(STATE2, STATE3)), 'b');
        assertEquals(3, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2, STATE3)));

        currentStates = simulation.nextStates(new HashSet<State>(Arrays.asList(STATE1, STATE2, STATE3)), 'a');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2)));
    }

    @Test
    public void test_rulesFor() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        simulation.rulesFor(new HashSet<State>(Arrays.asList(STATE1, STATE2)));
    }
}
