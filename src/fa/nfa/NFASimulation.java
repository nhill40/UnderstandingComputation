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

    public StatesAndRules discoverStatesAndRules(Set<Set<State>> states) {

        List<FAMultiRule> rules = new ArrayList<>();
        for (Set<State> multiState : states) {
            rules.addAll(rulesFor(multiState));
        }

        Set<Set<State>> moreStates = new HashSet<>();
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
                discoverStatesAndRules(new HashSet<>(Arrays.asList(startStates)));

        return statesAndRules.toDFADesign(startStates, nfaDesign);
    }

    protected static class FAMultiRule {
        private Set<State> states;
        private Character character;
        private Set<State> nextStates;

        public FAMultiRule(Set<State> states, Character character, Set<State> nextStates) {
            this.states = states;
            this.character = character;
            this.nextStates = nextStates;
        }

        /**
         * @return the nextState.
         */
        public Set<State> follow() {
            return nextStates;
        }

        @Override
        public String toString() {
            // This is intended as a testing/debugging convenience
            StringBuilder sb = new StringBuilder();
            sb.append(statesToString(states));
            sb.append(" ---").append(character).append("--> ");
            sb.append(statesToString(nextStates));
            return sb.toString();
        }

        public static String statesToString(Set<State> states) {
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
    }

    protected static class StatesAndRules {
        private Set<Set<State>> states;
        private List<FAMultiRule> rules;
        private Set<State> singleStates = new HashSet<>();

        private StatesAndRules(Set<Set<State>> states, List<FAMultiRule> rules) {
            this.states = states;
            this.rules = rules;
        }

        private Set<Set<State>> getStates() {
            return states;
        }

        private void setStates(Set<Set<State>> states) {
            this.states = states;
        }

        private List<FAMultiRule> getRules() {
            return rules;
        }

        private void setRules(List<FAMultiRule> rules) {
            this.rules = rules;
        }



        public FARule toSingleRule(FAMultiRule multiRule) {
            State state = State.buildState(multiRule.states);
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

            State nextState = State.buildState(multiRule.nextStates);
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
            for (Set<State> state : states) {
                if (nfaDesign.toNFA(state).accepting()) {
                    State acceptState = State.buildState(state);
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
