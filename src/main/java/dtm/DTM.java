package dtm;

import fa.State;

import java.util.List;

/**
 * A Deterministic Turing Machine - a type of computer that can be programmed to handle solving any problem.  It knows
 * (1) what state it is currently in as well as what the tape storage currently looks like (via TMConfiguration),
 * (2) what should be considered accept states, and (3) what rules dictate movement between states as well as what to
 * write/where to move next on the tape storage.
 */
public class DTM {
    private TMConfiguration currentConfiguration;
    private List<State> acceptStates;
    private DTMRulebook rulebook;

    public DTM(TMConfiguration currentConfiguration, List<State> acceptStates, DTMRulebook rulebook) {
        this.currentConfiguration = currentConfiguration;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Is the DTM currently in an accepting state?
     * @return if the DTM is in an accepting state.
     */
    public boolean accepting() {
        return acceptStates.contains(currentConfiguration.getState());
    }

    /**
     * Alters the state/tape of this DTM according to the next applicable rule.
     */
    public void step() {
        currentConfiguration = rulebook.nextConfiguration(currentConfiguration);
    }

    /**
     * Loops through the computation steps until this DTM either reaches an accept state or becomes stuck.
     */
    public void run() {
        while (!accepting() && !stuck()) {
            step();
        }
    }

    public TMConfiguration getCurrentConfiguration() {
        return currentConfiguration;
    }

    /**
     * Tells if this DTM is currently stuck (i.e. in a state/tape combination there is no way of getting out of).
     * @return whether or not the DTM is stuck.
     */
    public boolean stuck() {
        return !accepting() && !rulebook.appliesTo(currentConfiguration);
    }
}
