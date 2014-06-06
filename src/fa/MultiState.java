package fa;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a collection of states meant to be considered as an indivisible unit.  Note that, unlike plain old
 * "State", "equals()" implementation <b>has</b>been provided, as each unique instance of a collection of states should
 * be considered equivalent.
 */
public class MultiState implements State {
    private final Set<Integer> identifiers = new TreeSet<Integer>();

    public MultiState(Integer... identifiers) {
        this.identifiers.addAll(Arrays.asList(identifiers));
    }

    @Override
    public String toString() {
        return identifiers.toString();
    }

    // TODO: implement equals/hashcode
}
