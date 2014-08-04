package pda.dpda;

import fa.State;
import pda.PDAConfiguration;

import java.util.List;

/**
 * A slightly more complex computer: Deterministic Push Down Automaton.  It knows (1) what state it is currently in as
 * well as what its stack currently looks like (via PDAConfiguration), (2) what should be considered acceptStates, and
 * (3) what rules dictate movement between states.
 */
public class DPDA {
    private PDAConfiguration currentConfiguration;
    private List<State> acceptStates;
    private DPDARulebook rulebook;

    public DPDA(PDAConfiguration currentConfiguration, List<State> acceptStates, DPDARulebook rulebook) {
        this.currentConfiguration = currentConfiguration;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Is the DPDA currently in an accepting state?
     * @return if the DPDA is in an accepting state.
     */
    public boolean accepting() {
        return acceptStates.contains(getCurrentConfiguration().getState());
    }

    /**
     * Reads in a character and feeds it to the rulebook to move the configuration to the next state (and alter the
     * stack accordingly).
     * @param character the character to evaluate.
     */
    public void readCharacter(Character character) {
        currentConfiguration = nextConfiguration(character);
    }

    /**
     * Reads in a string, converts it to a CharArrray, then loops through each character.
     * @param string the characters to evaluate.
     */
    public void readString(String string) {
        for (Character character : string.toCharArray()) {
            if (!isStuck()) readCharacter(character);
        }
    }

    /**
     * Given some character input, modifies this DPDA's configuration (currents state + stack) accordingly.  The
     * rulebook reports back no applicable rule applies, will return a special type of configuration indicating itself
     * to be stuck.
     * @param character the character to evaluate against the rules.
     * @return the modified configuration (state + stack).
     */
    public PDAConfiguration nextConfiguration(Character character) {
        if (rulebook.appliesTo(getCurrentConfiguration(), character)) {
            return rulebook.nextConfiguration(getCurrentConfiguration(), character);
        } else {
            return getCurrentConfiguration().stuck();
        }
    }

    /**
     * Returns the current configuration (current state + stack) for this DPDA, taking any available free moves into
     * consideration.
     * @return The current state + stack (i.e. PDAConfiguration) taking any available free moves into consideration.
     */
    public PDAConfiguration getCurrentConfiguration() {
        return rulebook.followFreeMoves(currentConfiguration);
    }

    /**
     * Tells if this DPDA is currently stuck (i.e. in a state/stack combination there is no way of getting out of).
     * @return if the DPDA is stuck.
     */
    public boolean isStuck() {
        return getCurrentConfiguration().isStuck();
    }
}
