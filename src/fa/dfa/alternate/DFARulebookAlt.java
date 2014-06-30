package fa.dfa.alternate;

import fa.MultiState;
import fa.nfa.simulation.FAMultiRule;

import java.util.List;

public class DFARulebookAlt {
    private List<FAMultiRule> rules;

    public DFARulebookAlt(List<FAMultiRule> rules) {
        this.rules = rules;
    }

    /**
     * Looks up the appropriate rule based on state/character and invokes "follow()" on that rule to get us the next state.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return the next state.
     */
    public MultiState nextState(MultiState state, Character character) {
        return ruleFor(state, character).follow();
    }

    /**
     * Loops through its rules and returns the first one it finds that applies to the given state/character.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return the applicable rule.
     */
    public FAMultiRule ruleFor(MultiState state, Character character) {
        for (FAMultiRule rule : rules) {
            if (rule.appliesTo(state, character)) {
                return rule;
            }
        }

        throw new RuntimeException();
    }
}
