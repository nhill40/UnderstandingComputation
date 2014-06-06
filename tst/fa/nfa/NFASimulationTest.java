package fa.nfa;

import fa.State;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
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
        currentStates = simulation.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2)), 'a');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2)));

        currentStates = simulation.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2)), 'b');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE2, STATE3)));

        currentStates = simulation.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE2, STATE3)), 'b');
        assertEquals(3, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2, STATE3)));

        currentStates = simulation.nextStates(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2, STATE3)), 'a');
        assertEquals(2, currentStates.size());
        assertTrue(currentStates.containsAll(Arrays.asList(STATE1, STATE2)));
    }

    @Test
    public void test_rulebookAlphabet() {
        // Make sure our alphabet contains what we think it should
        Set<Character> results = NFA_RULEBOOK.alphabet();
        assertEquals(2, results.size());
        assertTrue(results.containsAll(Arrays.asList('a', 'b')));
    }

    /*@Test
    public void test_rulesFor() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        Set<FAMultiRule> rules = simulation.rulesFor(new LinkedHashSet<State>(Arrays.asList(STATE1, STATE2)));
        assertEquals(2, rules.size());
        List<String> rulesAsStrings = new ArrayList<String>();
        for (FAMultiRule rule : rules) {
            rulesAsStrings.add(rule.toString());
        }
        assertTrue(rulesAsStrings.containsAll(Arrays.asList("[1, 2] ---a--> [1, 2]", "[1, 2] ---b--> [2, 3]")));

        rules = simulation.rulesFor(new LinkedHashSet<State>(Arrays.asList(STATE3, STATE2)));
        assertEquals(2, rules.size());
        rulesAsStrings = new ArrayList<String>();
        for (FAMultiRule rule : rules) {
            rulesAsStrings.add(rule.toString());
        }
        assertTrue(rulesAsStrings.containsAll(Arrays.asList("[3, 2] ---a--> []", "[3, 2] ---b--> [2, 1, 3]")));
    }

    @Test
    public void test_discoverStatesAndRules() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        // Just a baseline to establish where we are at to begin with - 2 possible current states
        Set<State> startStates = nfaDesign.toNFA().getCurrentStatesConsideringFreeMoves();
        assertEquals(2, startStates.size());
        assertTrue(startStates.containsAll(Arrays.asList(STATE1, STATE2)));

        simulation.discoverStatesAndRules(startStates);
    }*/
}
