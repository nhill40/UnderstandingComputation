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

    public boolean isStuck() {
        return state instanceof StuckState;
    }

    public PDAConfiguration stuck() {
        return new PDAConfiguration(new StuckState(), stack);
    }
}
