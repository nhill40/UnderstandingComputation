package fa;

import java.util.Set;

public interface State {

    public Set<Integer> getIdentifiers();
    public Set<State> getStates();
}
