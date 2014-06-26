package fa.nfa.simulation;

import fa.FARule;
import fa.State;
import fa.dfa.DFADesign;
import fa.dfa.DFARulebook;
import fa.nfa.NFADesign;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// TODO: this was originally intended as datastructure convienence (to combine a set of MultiStates and a list of MultiRules into one datastructure), but it took on a life of its own once I put "toDFADesign" in here - a design choice that needs to be re-evaluated!
public class StatesAndRules {
    private Set<MultiState> states;
    private List<FAMultiRule> rules;
    private Set<State> singleStates = new HashSet<>();

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

    public FARule toSingleRule(FAMultiRule multiRule) {
        State state = State.buildState(multiRule.getStates().getStates());
        if (contains(singleStates, state)) {
            for (State singleState : singleStates) {
                if (singleState.isEquivalentTo(state)) {
                    state = singleState;
                    break;
                }
            }
        } else {
            singleStates.add(state);
        }

        State nextState = State.buildState(multiRule.getNextStates().getStates());
        if (contains(singleStates, nextState)) {
            for (State singleState : singleStates) {
                if (singleState.isEquivalentTo(nextState)) {
                    nextState = singleState;
                    break;
                }
            }
        } else {
            singleStates.add(nextState);
        }

        return new FARule(state, multiRule.getCharacter(), nextState);
    }

    private List<FARule> getRulesAsSingleRules() {
        List<FARule> result = new ArrayList<>();
        for (FAMultiRule multiRule : rules) {
            result.add(toSingleRule(multiRule));
        }
        return result;
    }

    public DFADesign toDFADesign(Set<State> startStates, NFADesign nfaDesign) {

        List<FARule> singleRules = getRulesAsSingleRules();
        for (FARule singleRule : singleRules) {
            if (!contains(singleStates, singleRule.getState())) singleStates.add(singleRule.getState());
            if (!contains(singleStates, singleRule.getNextState())) singleStates.add(singleRule.getNextState());
        }

        State startState = State.buildState(startStates);
        for (State singleState : singleStates) {
            if (singleState.isEquivalentTo(startState)) {
                startState = singleState;
                break;
            }
        }

        List<State> acceptStates = new ArrayList<>();
        for (MultiState state : states) {
            if (nfaDesign.toNFA(state.getStates()).accepting()) {
                State acceptState = State.buildState(state.getStates());
                for (State singleState : singleStates) {
                    if (singleState.isEquivalentTo(acceptState)) {
                        acceptStates.add(singleState);
                        break;
                    }
                }
            }
        }
        return new DFADesign(startState, acceptStates,
                new DFARulebook(singleRules));
    }

    private static boolean contains(Set<State> singleStates, State singleState) {
        for (State state : singleStates) {
            if (state.isEquivalentTo(singleState)) return true;
        }
        return false;
    }
}
