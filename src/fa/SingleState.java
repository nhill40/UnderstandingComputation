package fa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a simple state with a single identifier.
 */
public class SingleState implements State {
    private final Integer identifier;

    public SingleState(Integer identifier) {
        this.identifier = identifier;
    }

    @Override
    public Set<State> getStates() {
        Set<State> states = new HashSet<>();
        states.add(this);
        return states;
    }

    @Override
    public String toString() {
        return identifier.toString();
    }

    @Override
    public Set<Integer> getIdentifiers() {
        return new HashSet<>(Arrays.asList(identifier));
    }
}
