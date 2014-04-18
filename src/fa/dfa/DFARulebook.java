package fa.dfa;

import fa.FARule;
import fa.State;

import java.util.List;

/**
 * Essentially, a collection of rules.
 * (1) A collection of rules
 * (2) Logic for how to lookup the appropriate rule for a given state/input
 * (3) A call to "follow" on the looked up rule to get next state.
 */
public class DFARulebook {
    private List<FARule> rules;

    public DFARulebook(List<FARule> rules) {
        this.rules = rules;
    }

    /**
     * Looks up the appropriate rule based on state/character and invokes "follow()" on that rule to get us the next state.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return the next state.
     */
    public State nextState(State state, char character) {
        return ruleFor(state, character).follow();
    }

    /**
     * Loops through its rules and returns the first one it finds that applies to the given state/character.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return the applicable rule.
     */
    public FARule ruleFor(State state, char character) {
        for (FARule rule : rules) {
            if (rule.appliesTo(state, character)) {
                return rule;
            }
        }

        throw new RuntimeException();
    }
}
