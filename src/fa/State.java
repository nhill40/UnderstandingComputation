package fa;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a collection of states meant to be considered as an indivisible unit.  Note that, unlike plain old
 * "State", "equals()" implementation <b>has</b>been provided, as each unique instance of a collection of states should
 * be considered equivalent.
 */
public class State {
    private final Set<Integer> identifiers = new TreeSet<>();

    public State(Integer... identifiers) {
        if (identifiers != null)
            this.identifiers.addAll(Arrays.asList(identifiers));
    }

    @Override
    public String toString() {
        return identifiers.toString();
    }

    public Set<Integer> getIdentifiers() {
        return identifiers;
    }

    public static State buildState(Set<State> states) {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }

        return new State(identifiers.toArray(new Integer[identifiers.size()]));
    }

    // TODO: implement equals/hashcode (?)
}
