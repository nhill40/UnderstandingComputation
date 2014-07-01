package fa.nfa.simulation;

import fa.FARule;
import fa.MultiState;

import java.util.List;
import java.util.Set;

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
