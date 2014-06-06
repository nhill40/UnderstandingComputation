package fa.nfa;

import fa.FARule;
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
    public Set<State> nextStates(Set<State> states, Character character) {
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
    public Set<FARule> rulesFor(Set<Set<State>> states) {
        Set<FARule> results = new LinkedHashSet<FARule>();

        for (Character character : nfaDesign.getRulebook().alphabet()) {
            //results.add(new FAMultiRule(states, character, nextStates(states, character)));
        }
        return results;
    }

    // TODO:  see page 142 - implement "discoverStatesAndRules"
    public Map<Set<Set<State>>, List<FARule>> discoverStatesAndRules(Set<Set<State>> states) {
        Map<Set<Set<State>>, List<FARule>> results = new HashMap<Set<Set<State>>, List<FARule>>();
        Set<Set<State>> resultStates = new HashSet<Set<State>>();
        List<FARule> resultRules = new ArrayList<FARule>();

        Set<FARule> rules = new HashSet<FARule>();
        rules.addAll(rulesFor(states));
        Set<State> moreStates = new HashSet<State>();
        for (FARule rule : rules) {
            //moreStates.addAll(rule.follow());
        }

        if (states.containsAll(moreStates)) {
            //resultStates.add(states);
            resultRules.addAll(rules);
            results.put(resultStates, resultRules);
            return results;
        } else {
            Set<State> yetEvenMoreStates = new HashSet<State>();
            //yetEvenMoreStates.addAll(states);
            yetEvenMoreStates.addAll(moreStates);
           // results = discoverStatesAndRules(yetEvenMoreStates);
        }

        return results;
    }
}
