package fa.nfa;

import fa.State;

import java.util.List;
import java.util.Set;

public class NFA {

    private Set<State> currentStates;
    private List<State> acceptStates;
    private NFARulebook rulebook;

    public NFA(Set<State> currentStates, List<State> acceptStates, NFARulebook rulebook) {
        this.currentStates = currentStates;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Is the NFA currently in an accepting state? (i.e. are any of my current states an accept state?)
     * @return whether or not the NFA is in accepting state.
     */
    public boolean accepting() {
        boolean accepting = false;

        for (State currentState : currentStates) {
            if (acceptStates.contains(currentState)) {
                accepting = true;
                break;
            }
        }

        return accepting;
    }

    /**
     * Reads in a character, passes it and the current states to the rulebook to get the next possible states.
     * @param character the character to evaluate.
     */
    public void readCharacter(Character character) {
        currentStates = rulebook.nextStates(getCurrentStatesConsideringFreeMoves(), character);
    }

    /**
     * Reads in a string, converts it to a CharArrray, then loops through each character.
     * @param string the characters to evaluate.
     */
    public void readString(String string) {
        if (string.toCharArray().length == 0) {
            readCharacter('\0');
        } else {
            for (Character character : string.toCharArray()) {
                readCharacter(character);
            }
        }
    }

    /**
     * Provides current states taking any possible free moves into consideration.
     * @return Possible current states taking free moves into consideration.
     */
    private Set<State> getCurrentStatesConsideringFreeMoves() {
        return rulebook.followFreeMoves(currentStates);
    }

}
