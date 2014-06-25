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
        if (identifiers != null) {
            for (Integer identifier : Arrays.asList(identifiers)) {
                if (identifier != null) this.identifiers.add(identifier);
            }
        }
    }

    public State(Set<Integer> identifiers) {
        if (identifiers != null)
            this.identifiers.addAll(identifiers);
    }

    @Override
    public String toString() {
        return identifiers.toString();
    }

    public Set<Integer> getIdentifiers() {
        return identifiers;
    }

    public boolean isEquivalentTo(State otherState) {
        return this.getIdentifiers().size() == otherState.getIdentifiers().size()
                && this.getIdentifiers().containsAll(otherState.getIdentifiers());
    }

    public static State buildState(Set<State> states) {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }

        return new State(identifiers.toArray(new Integer[identifiers.size()]));
    }

    public static boolean isSubset(Set<Set<State>> potentialSuperset, Set<Set<State>> potentialSubset) {
        // Save some machine cycles - we know "false" right off the bat if the superset is < than the subset.
        if (potentialSuperset.size() < potentialSubset.size()) {
            return false;
        }

        Set<Integer> supersetIdentifiers = new TreeSet<>();
        for (Set<State> states : potentialSuperset) {
            supersetIdentifiers.addAll(getIdentifiers(states));
        }

        Set<Integer> subsetIdentifiers = new TreeSet<>();
        for (Set<State> states : potentialSubset) {
            subsetIdentifiers.addAll(getIdentifiers(states));
        }

        return supersetIdentifiers.containsAll(subsetIdentifiers);
    }

    public static Set<Integer> getIdentifiers(Set<State> states) {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }
        return identifiers;
    }
}
