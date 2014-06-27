package fa.nfa.simulation;

import java.util.List;
import java.util.Set;

public class StatesAndRules {
    private Set<MultiState> states;
    private List<FAMultiRule> rules;

    public StatesAndRules(Set<MultiState> states, List<FAMultiRule> rules) {
        this.states = states;
        this.rules = rules;
    }

    public Set<MultiState> getStates() {
        return states;
    }

    public void setStates(Set<MultiState> states) {
        this.states = states;
    }

    public List<FAMultiRule> getRules() {
        return rules;
    }

    public void setRules(List<FAMultiRule> rules) {
        this.rules = rules;
    }
}
