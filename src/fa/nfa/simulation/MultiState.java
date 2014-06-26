package fa.nfa.simulation;

import fa.State;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public  class MultiState {
    private Set<State> states;

    public MultiState(State... states) {
        this.states = new HashSet<>(Arrays.asList(states));
    }

    public MultiState(Set<State> states) {
        this.states = states;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        String prefix = "";
        for (Integer identifier : getIdentifiers()) {
            sb.append(prefix).append(identifier);
            prefix = ", ";
        }
        sb.append(']');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof MultiState)) return false;
        MultiState otherMultiState = (MultiState) o;

        if (states.size() != otherMultiState.getStates().size()) return false;

        if (!states.containsAll(otherMultiState.getStates())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return states.hashCode();
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
    public static boolean isSubset(Set<MultiState> potentialSuperset, Set<MultiState> potentialSubset) {
        // Save some machine cycles - we know "false" right off the bat if the superset is < than the subset.
        if (potentialSuperset.size() < potentialSubset.size()) {
            return false;
        }

        Set<Integer> supersetIdentifiers = new TreeSet<>();
        for (MultiState states : potentialSuperset) {
            supersetIdentifiers.addAll(states.getIdentifiers());
        }

        Set<Integer> subsetIdentifiers = new TreeSet<>();
        for (MultiState states : potentialSubset) {
            subsetIdentifiers.addAll(states.getIdentifiers());
        }

        return supersetIdentifiers.containsAll(subsetIdentifiers);
    }

    public Set<Integer> getIdentifiers() {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }
        return identifiers;
    }
}