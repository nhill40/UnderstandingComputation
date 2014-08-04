package fa;

import java.util.Set;

public class StuckState implements State {

    @Override
    public Set<Integer> getIdentifiers() {
        return null;  // NO OP
    }

    @Override
    public Set<SingleState> getStates() {
        return null;  // NO OP
    }

    @Override
    public String toString() {
        return "Stuck";
    }
}
