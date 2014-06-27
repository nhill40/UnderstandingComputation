package fa.dfa.alternate;

import fa.nfa.simulation.MultiState;

import java.util.List;
import java.util.Set;

public class DFAAlt {
    private MultiState currentState;
    private Set<MultiState> acceptStates;
    private DFARulebookAlt rulebook;

    public DFAAlt(MultiState currentState, Set<MultiState> acceptStates, DFARulebookAlt rulebook) {
        this.currentState = currentState;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Is the DFA currently in an accepting state?
     * @return if the DFA is in an accepting state.
     */
    public boolean accepting() {
        return acceptStates.contains(currentState);
    }

    /**
     * Reads in a character, passes it and the current state to the rulebook to move itself to the next state.
     * @param character the character to evaluate.
     */
    public void readCharacter(Character character) {
        currentState = rulebook.nextState(currentState, character);
    }

    /**
     * Reads in a string, converts it to a CharArrray, then loops through each character.
     * @param string the characters to evaluate.
     */
    public void readString(String string) {
        for (Character character : string.toCharArray()) {
            readCharacter(character);
        }
    }
}
