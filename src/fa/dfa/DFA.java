package fa.dfa;

import fa.State;

import java.util.List;

/**
 * A simple computer: Deterministic Finite Automaton.  It knows (1) what state it is currently in, (2) what should be
 * considered acceptStates, and (3) what rules dictate movement between states.
 */
public class DFA {
    private State currentState;
    private List<State> acceptStates;
    private DFARulebook rulebook;

    public DFA(State currentState, List<State> acceptStates, DFARulebook rulebook) {
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
    public void readCharacter(char character) {
        currentState = rulebook.nextState(currentState, character);
    }

    /**
     * Reads in a string, converts it to a CharArrray, then loops through each character.
     * @param string the characters to evaluate.
     */
    public void readString(String string) {
        for (char character : string.toCharArray()) {
            readCharacter(character);
        }
    }
}
