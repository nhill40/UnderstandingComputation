package fa.nfa.simulation;

import fa.FARule;
import fa.MultiState;

import java.util.List;
import java.util.Set;

/**
 * Data structure to capture collections of states and rules as an intermediate step in between the NFA to DFA
 * conversion process (an alternative to doing something convoluted like "Map<Set<MultiState>, List<FARule>>"
 */
public class StatesAndRules {
    private Set<MultiState> states;
    private List<FARule> rules;

    public StatesAndRules(Set<MultiState> states, List<FARule> rules) {
        this.states = states;
        this.rules = rules;
    }

    public Set<MultiState> getStates() {
        return states;
    }

    public void setStates(Set<MultiState> states) {
        this.states = states;
    }

    public List<FARule> getRules() {
        return rules;
    }

    public void setRules(List<FARule> rules) {
        this.rules = rules;
    }
}
