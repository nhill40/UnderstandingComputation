package pda.npda;

import fa.State;
import pda.PDAConfiguration;

import java.util.List;
import java.util.Set;

/**
 * A slightly more complex computer: Non-deterministic Push Down Automaton.  It knows (1) what state it is (possibly)
 * currently in as well as what its stack currently (possibly) looks like (via PDAConfiguration), (2) what should be
 * considered acceptStates, and (3) what rules dictate movement between states.
 */
public class NPDA {
    private Set<PDAConfiguration> currentConfigurations;
    private List<State> acceptStates;
    private NPDARulebook rulebook;

    public NPDA(Set<PDAConfiguration> currentConfigurations, List<State> acceptStates, NPDARulebook rulebook) {
        this.currentConfigurations = currentConfigurations;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Is the NPDA currently in an accepting state?
     * @return if the NPDA is in an accepting state.
     */
    public boolean accepting() {
        for (PDAConfiguration currentConfiguration : getCurrentConfigurations()) {
            if (acceptStates.contains(currentConfiguration.getState())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Reads in a character and feeds it to the rulebook to get the possible next configurations.
     * @param character the character to evaluate.
     */
    public void readCharacter(Character character) {
        currentConfigurations = rulebook.nextConfigurations(getCurrentConfigurations(), character);
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

    /**
     * Returns the current possible configurations (current state + stack) for this NPDA, taking any available free
     * moves into consideration.
     * @return The possible current state + stack combinations (i.e. PDAConfigurations) taking any available free moves
     *         into consideration.
     */
    public Set<PDAConfiguration> getCurrentConfigurations() {
        return rulebook.followFreeMoves(currentConfigurations);
    }
}
