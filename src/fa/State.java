package fa;

import fa.nfa.NFASimulation;

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

    /**
     * Very suspicious method that came from not being able to provide a traditional equals() implementation (see note
     * above on "isEquivalentTo()" method).  Because we can't provide an equals, I'm basically recreating the
     * functionality we should get for free from the "contains()" and "containsAll()" methods.
     * @param potentialSuperset The set of states to check for being a potential superset of the provided potential
     *                          subset.
     * @param potentialSubset The set of states to check for being a potential subset of the provided potential
     *                        superset.
     * @return <code>true</code> if there is a superset/subset relationship, <code>false</code> otherwise.
     */
    public static boolean isSubset(Set<NFASimulation.MultiState> potentialSuperset, Set<NFASimulation.MultiState> potentialSubset) {
        // Save some machine cycles - we know "false" right off the bat if the superset is < than the subset.
        if (potentialSuperset.size() < potentialSubset.size()) {
            return false;
        }

        Set<Integer> supersetIdentifiers = new TreeSet<>();
        for (NFASimulation.MultiState states : potentialSuperset) {
            supersetIdentifiers.addAll(getIdentifiers(states.getStates()));
        }

        Set<Integer> subsetIdentifiers = new TreeSet<>();
        for (NFASimulation.MultiState states : potentialSubset) {
            subsetIdentifiers.addAll(getIdentifiers(states.getStates()));
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
