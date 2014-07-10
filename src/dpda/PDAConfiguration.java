package dpda;

import fa.State;
import fa.StuckState;

/**
 * A PDA configuration is the combination of current state and a stack.
 */
public class PDAConfiguration {
    private State state;
    private Stack stack;

    public PDAConfiguration(State state, Stack stack) {
        this.state = state;
        this.stack = stack;
    }

    public State getState() {
        return state;
    }

    public Stack getStack() {
        return stack;
    }

    @Override
    public String toString() {
        return "state=" + state + ", stack=" + stack;
    }

    /**
     * Whether or not this PDAConfiguration should be considered "stuck" based on its state.
     * @return <code>true</code> if this configuration is stuck, <code>false</code> if otherwise.
     */
    public boolean isStuck() {
        return state instanceof StuckState;
    }

    /**
     * Returns a new configuration using this configuration's current stack but using a "Stuck State" in place of its
     * current state.
     * @return the new "stuck" configuration.
     */
    public PDAConfiguration stuck() {
        return new PDAConfiguration(new StuckState(), stack);
    }
}
