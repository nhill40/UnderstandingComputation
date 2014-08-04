package dtm;

import fa.State;

/**
 * A TM configuration is the combination of current state and a tape.
 */
public class TMConfiguration {
    private State state;
    private Tape tape;

    public TMConfiguration(State state, Tape tape) {
        this.state = state;
        this.tape = tape;
    }

    public State getState() {
        return state;
    }

    public Tape getTape() {
        return tape;
    }

    @Override
    public String toString() {
        return "state=" + state + ", tape=" + tape;
    }
}
