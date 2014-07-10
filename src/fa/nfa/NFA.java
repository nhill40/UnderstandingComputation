package fa.nfa;

import fa.SingleState;

import java.util.List;
import java.util.Set;

/**
 * A simple computer: Non-Deterministic Finite Automaton.  It knows (1) what states it is possibly in, (2) what should
 * be considered acceptStates, and (3) what rules dictate movement between states.
 */
public class NFA {

    // TODO: not 100% sold that we should explicitly deal with SingleState here, refactoring may have been overzealous - might have been smarter to leave this as the "State" interface.
    // Refer to "Brzozowski's Algorithm" where we reverse a DFA, producing a new NFA (which would need to deal with MultiStates).
    private Set<SingleState> currentStates;
    private List<SingleState> acceptStates;
    private NFARulebook rulebook;

    public NFA(Set<SingleState> currentStates, List<SingleState> acceptStates, NFARulebook rulebook) {
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

        for (SingleState currentState : getCurrentStates()) {
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
        currentStates = rulebook.nextStates(getCurrentStates(), character);
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
    public Set<SingleState> getCurrentStates() {
        return rulebook.followFreeMoves(currentStates);
    }

}
