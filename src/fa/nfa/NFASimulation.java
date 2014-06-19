package fa.nfa;

import fa.FAMultiRule;
import fa.FARule;
import fa.FASingleRule;
import fa.State;

import java.util.*;

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
    public Set<State> nextState(Set<State> states, Character character) {
        NFA nfa = nfaDesign.toNFA(states);
        nfa.readCharacter(character);
        return nfa.getCurrentStatesConsideringFreeMoves();
    }

    /**
     * Builds a collection of "Multi Rules" (i.e. rules that can have 1:M current states and 1:M next states).  To
     * accomplish this, we loop through the provided NFADesign's "alphabet" (possible/valid input characters) and build
     * a "Multi Rule" for each one where the current states have been provided by the caller and the next states have
     * been calculated by invoking nextStates.
     * @param states The current states to be used when building the Multi Rule.
     * @return A collection of Multi Rules covering every possible input character.
     */
    public List<FAMultiRule> rulesFor(Set<State> states) {
        List<FAMultiRule> results = new ArrayList<>();
        for (Character character : nfaDesign.getRulebook().alphabet()) {
            results.add(new FAMultiRule(states, character, nextState(states, character)));
        }
        return results;
    }

    // TODO: these data structures are out of control - I think we need a "MultiState" data structure to represent sets-of-states
    public Map<Set<Set<State>>, List<FAMultiRule>> discoverStatesAndRules(Set<Set<State>> states) {

        List<FAMultiRule> rules = new ArrayList<>();
        for (Set<State> stateSet : states) {
            rules.addAll(rulesFor(stateSet));
        }

        Set<Set<State>> moreStates = new HashSet<>();
        for (FAMultiRule rule : rules) {
            moreStates.add(rule.follow());
        }

        if (isSubset(states, moreStates)) {
            Map<Set<Set<State>>, List<FAMultiRule>> results = new HashMap<>();
            results.put(states, rules);
            return results;
        } else {
            states.addAll(moreStates);
            return discoverStatesAndRules(states);
        }
    }

    // TODO: this method is imprecise - only doing a quick sanity check to compare sizes, but needs to deep dive to determine if truly a superset/subset situation
    // TODO: probably belongs on the "MultiState" class if/when we have one
    public static boolean isSubset(Set<Set<State>> potentialSuperset, Set<Set<State>> potentialSubset) {
        // We know "false" right off the bat if the superset is < than the subset.
        if (potentialSuperset.size() < potentialSubset.size()) {
            return false;
        }
        return true;
    }
}
