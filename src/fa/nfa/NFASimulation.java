package fa.nfa;

import fa.FARule;
import fa.State;
import fa.dfa.DFADesign;
import fa.dfa.DFARulebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        if (State.isSubset(states, moreStates)) {
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

    public static class MultiState {
        private Set<State> states;

        public MultiState(State... states) {
            this.states = new HashSet<>(Arrays.asList(states));
        }

        public MultiState(Set<State> states) {
            this.states = states;
        }

        public Set<State> getStates() {
            return states;
        }

        public void setStates(Set<State> states) {
            this.states = states;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            String prefix = "";
            for (Integer identifier : State.getIdentifiers(states)) {
                sb.append(prefix).append(identifier);
                prefix = ", ";
            }
            sb.append(']');
            return sb.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !(o instanceof MultiState)) return false;
            MultiState otherMultiState = (MultiState) o;

            if (states.size() != otherMultiState.getStates().size()) return false;

            if (!states.containsAll(otherMultiState.getStates())) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return states.hashCode();
        }
    }

    protected static class FAMultiRule {
        private MultiState states;
        private Character character;
        private MultiState nextStates;

        public FAMultiRule(MultiState states, Character character, MultiState nextStates) {
            this.states = states;
            this.character = character;
            this.nextStates = nextStates;
        }

        /**
         * @return the nextState.
         */
        public MultiState follow() {
            return nextStates;
        }

        @Override
        public String toString() {
            // This is intended as a testing/debugging convenience
            StringBuilder sb = new StringBuilder();
            sb.append(states);
            sb.append(" ---").append(character).append("--> ");
            sb.append(nextStates);
            return sb.toString();
        }
    }

    protected static class StatesAndRules {
        private Set<MultiState> states;
        private List<FAMultiRule> rules;
        private Set<State> singleStates = new HashSet<>();

        private StatesAndRules(Set<MultiState> states, List<FAMultiRule> rules) {
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
            State state = State.buildState(multiRule.states.getStates());
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

            State nextState = State.buildState(multiRule.nextStates.getStates());
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

            return new FARule(state, multiRule.character, nextState);
        }

        private List<FARule> getRulesAsSingleRules() {
            List<FARule> result = new ArrayList<>();
            for (FAMultiRule multiRule : rules) {
                result.add(toSingleRule(multiRule));
            }
            return result;
        }

        private DFADesign toDFADesign(Set<State> startStates, NFADesign nfaDesign) {

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
}
