package fa.nfa.simulation;

import fa.State;
import fa.dfa.DFADesign;
import fa.nfa.NFA;
import fa.nfa.NFADesign;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: this class is getting too heavy-duty - refactor so it lives in its own package and then split out all of
// these inner classes into standalone classes living in that same package.
public class NFASimulation {
    private NFADesign nfaDesign;

    public NFASimulation(NFADesign nfaDesign) {
        this.nfaDesign = nfaDesign;
    }

    /**
     * Creates an NFA off of the provided NFADesign based on a set of current states and reading in an input character.
     * We then ask that NFA what it's current states are (taking possible free moves into consideration).
     * @param states The current states to consider when building the NFA.
     * @param character The input character to feed the NFA.
     * @return The possible current states taking free moves into consideration.
     */
    public MultiState nextState(MultiState states, Character character) {
        NFA nfa = nfaDesign.toNFA(states.getStates());
        nfa.readCharacter(character);
        return new MultiState(nfa.getCurrentStatesConsideringFreeMoves());
    }

    /**
     * Builds a collection of "Multi Rules" (i.e. rules that can have 1:M current states and 1:M next states).  To
     * accomplish this, we loop through the provided NFADesign's "alphabet" (possible/valid input characters) and build
     * a "Multi Rule" for each one where the current states have been provided by the caller and the next states have
     * been calculated by invoking nextStates.
     * @param states The current states to be used when building the Multi Rule.
     * @return A collection of Multi Rules covering every possible input character.
     */
    public List<FAMultiRule> rulesFor(MultiState states) {
        List<FAMultiRule> results = new ArrayList<>();
        for (Character character : nfaDesign.getRulebook().alphabet()) {
            results.add(new FAMultiRule(states, character, nextState(states, character)));
        }
        return results;
    }

    public StatesAndRules discoverStatesAndRules(Set<MultiState> states) {

        List<FAMultiRule> rules = new ArrayList<>();
        for (MultiState multiState : states) {
            rules.addAll(rulesFor(multiState));
        }

        Set<MultiState> moreStates = new HashSet<>();
        for (FAMultiRule rule : rules) {
            moreStates.add(rule.follow());
        }

        if (states.containsAll(moreStates)) {
            return new StatesAndRules(states, rules);
        } else {
            states.addAll(moreStates);
            return discoverStatesAndRules(states);
        }
    }

    public DFADesign toDFADesign() {
        Set<State> startStates = nfaDesign.toNFA().getCurrentStatesConsideringFreeMoves();
        StatesAndRules statesAndRules =
                discoverStatesAndRules(new HashSet<>(Arrays.asList(new MultiState(startStates))));

        return statesAndRules.toDFADesign(startStates, nfaDesign);
    }
}
