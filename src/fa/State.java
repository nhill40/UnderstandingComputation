package fa;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a state.
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

    /**
     * Very suspicious method that is an alternative to a more traditional equals() override.  This is how I would code
     * the equals, but I don't want to actually provide that implementation because I technically need states to be
     * considered not equal even when their set of identifiers are perfect matches
     * @param otherState The state to check this one against.
     * @return <code>true</code> if this state matches the provided otherState, <code>false</code> if otherwise.
     */
    public boolean isEquivalentTo(State otherState) {
        return this.getIdentifiers().size() == otherState.getIdentifiers().size()
                && this.getIdentifiers().containsAll(otherState.getIdentifiers());
    }

    /**
     * Very suspicious method that allows us to build a single, new indivisible state based upon a set of states.  This
     * is done by looping through the provided states, getting all of their identifiers, and then mashing them all
     * together into a new state.
     * @param states The states to use to construct the new state.
     * @return The newly created state.
     */
    public static State buildState(Set<State> states) {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }

        return new State(identifiers.toArray(new Integer[identifiers.size()]));
    }
}
