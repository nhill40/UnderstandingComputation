package fa;

import java.util.Set;

/**
 * A common interface for all State subtypes to adhere to.
 */
public interface State {

    public Set<Integer> getIdentifiers();
    public Set<SingleState> getStates();
}
