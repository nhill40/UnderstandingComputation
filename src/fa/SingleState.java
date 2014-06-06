package fa;

/**
 * Represents a state.  Note that no "equals()" implementation has been provided as each new instance of a state should
 * be considered unique (i.e. only "equal to" itself).
 */
public class SingleState implements State {
    private final Integer identifier;

    public SingleState(Integer identifier) {
        this.identifier = identifier;
    }

    @Override
    public String toString() {
        return identifier.toString();
    }
}
