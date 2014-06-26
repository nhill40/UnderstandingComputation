package fa.nfa;

import fa.State;
import fa.dfa.DFADesign;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.nfa.NFATestRules.NFA_RULEBOOK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class NFASimulationTest {

    @Test
    public void test_nextStates() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        NFASimulation.MultiState currentStates;
        currentStates = simulation.nextState(new NFASimulation.MultiState(STATE1, STATE2), 'a');
        assertEquals(2, currentStates.getStates().size());
        assertTrue(currentStates.getStates().containsAll(Arrays.asList(STATE1, STATE2)));

        currentStates = simulation.nextState(new NFASimulation.MultiState(STATE1, STATE2), 'b');
        assertEquals(2, currentStates.getStates().size());
        assertTrue(currentStates.getStates().containsAll(Arrays.asList(STATE2, STATE3)));

        currentStates = simulation.nextState(new NFASimulation.MultiState(STATE2, STATE3), 'b');
        assertEquals(3, currentStates.getStates().size());
        assertTrue(currentStates.getStates().containsAll(Arrays.asList(STATE1, STATE2, STATE3)));

        currentStates = simulation.nextState(new NFASimulation.MultiState(STATE1, STATE2, STATE3), 'a');
        assertEquals(2, currentStates.getStates().size());
        assertTrue(currentStates.getStates().containsAll(Arrays.asList(STATE1, STATE2)));
    }

    @Test
    public void test_rulebookAlphabet() {
        // Make sure our alphabet contains what we think it should
        Set<Character> results = NFA_RULEBOOK.alphabet();
        assertEquals(2, results.size());
        assertTrue(results.containsAll(Arrays.asList('a', 'b')));
    }

    @Test
    public void test_rulesFor() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        List<NFASimulation.FAMultiRule> rules = simulation.rulesFor(new NFASimulation.MultiState(STATE1, STATE2));
        assertEquals(2, rules.size());
        List<String> rulesAsStrings = new ArrayList<>();
        for (NFASimulation.FAMultiRule rule : rules) {
            rulesAsStrings.add(rule.toString());
        }
        assertTrue(rulesAsStrings.containsAll(Arrays.asList("[1, 2] ---a--> [1, 2]", "[1, 2] ---b--> [2, 3]")));

        rules = simulation.rulesFor(new NFASimulation.MultiState(STATE2, STATE3));
        assertEquals(2, rules.size());
        rulesAsStrings = new ArrayList<>();
        for (NFASimulation.FAMultiRule rule : rules) {
            rulesAsStrings.add(rule.toString());
        }
        assertTrue(rulesAsStrings.containsAll(Arrays.asList("[2, 3] ---a--> []", "[2, 3] ---b--> [1, 2, 3]")));
    }

    @Test
    public void test_discoverStatesAndRules() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        // Just a baseline to establish where we are at to begin with - 2 possible current states
        Set<State> startStates = nfaDesign.toNFA().getCurrentStatesConsideringFreeMoves();
        assertEquals(2, startStates.size());
        assertTrue(startStates.containsAll(Arrays.asList(STATE1, STATE2)));

       NFASimulation.StatesAndRules result =
                simulation.discoverStatesAndRules(new HashSet<>(Arrays.asList(new NFASimulation.MultiState(startStates))));

        assertEquals(4, result.getStates().size());
        assertEquals(8, result.getRules().size());
    }

    @Test
    public void test_toDFADesign() {
        NFADesign nfaDesign = new NFADesign(STATE1, Arrays.asList(STATE3), NFA_RULEBOOK);
        NFASimulation simulation = new NFASimulation(nfaDesign);

        assertFalse(nfaDesign.accepts("aaa"));
        assertTrue(nfaDesign.accepts("aab"));
        assertTrue(nfaDesign.accepts("bbbabb"));

        DFADesign dfaDesign = simulation.toDFADesign();
        assertFalse(dfaDesign.accepts("aaa"));
        assertTrue(dfaDesign.accepts("aab"));
        assertTrue(dfaDesign.accepts("bbbabb"));
    }

}
