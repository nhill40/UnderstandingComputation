package fa.nfa;

import fa.FASingleRule;
import fa.State;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NFARulebook {
    private List<FASingleRule> rules;

    public NFARulebook(List<FASingleRule> rules) {
        this.rules = rules;
    }

    /**
     * Given a list of current states, recursively evaluate what the possible next states could be if a free move
     * spontaneously (<- which is the only way a free move can occur, BTW) occurs.
     * @param states the current states (this method recursively adds to this collection).
     * @return the current states after considering all possible free moves.
     */
    public Set<State> followFreeMoves(Set<State> states) {
        Set<State> moreStates = nextStates(states, null);

        if (states.containsAll(moreStates)) {
            return states;
        }

        Set<State> combinedStates = new HashSet<>();
        combinedStates.addAll(moreStates);
        combinedStates.addAll(states);
        states = followFreeMoves(combinedStates);

        return states;
    }

    /**
     * Looks up the appropriate rules based on states/character and use them to get us a list of next possible states.
     * @param states the different states to search the rules for.
     * @param character the character to search the rules for.
     * @return the next possible states.
     */
    public Set<State> nextStates(Set<State> states, Character character) {
        Set<State> possibleStates = new HashSet<>();

        for (State state : states) {
            possibleStates.addAll(followRulesFor(state, character));
        }

        return possibleStates;
    }

    /**
     * For a given state/character, lookup all applicable rules and invoke "follow()" on each one to build us a list of
     * all the possible next states.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return
     */
    public Set<State> followRulesFor(State state, Character character) {
        Set<State> possibleStates = new HashSet<>();

        for (FASingleRule rule : rulesFor(state, character)) {
            possibleStates.add(rule.follow());
        }

        return possibleStates;
    }

    /**
     * Loops through its rules and returns the first one it finds that applies to the given state/character.
     * @param state the state to search the rules for.
     * @param character the character to search the rules for.
     * @return the applicable rule.
     */
    public Set<FASingleRule> rulesFor(State state, Character character) {
        Set<FASingleRule> applicableRules = new HashSet<>();
        for (FASingleRule rule : rules) {
            if (rule.appliesTo(state, character)) {
                applicableRules.add(rule);
            }
        }

        return applicableRules;
    }

    public List<FASingleRule> getRules() {
        return rules;
    }

    /**
     * Loops through the rules associated with this rule book and builds a list of all possible input characters.
     * @return A list representing all possible/valid input characters for this rulebook.
     */
    public Set<Character> alphabet() {
        Set<Character> results = new HashSet<>();
        for (FASingleRule rule : rules) {
            if (rule.getCharacter() != null) results.add(rule.getCharacter());
        }
        return results;
    }
}
