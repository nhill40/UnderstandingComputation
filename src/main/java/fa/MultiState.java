package fa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a complex state which is comprised of several single states.  Perhaps the most important difference
 * between this type of state and a "SingleState" is that 2 MultiStates *can* be considered equivalent as long as their
 * respective state collections are equivalent (appropriate equals() and hashCode() methods have been provided).
 */
public  class MultiState implements State {
    private Set<SingleState> states;

    public MultiState(SingleState... states) {
        this.states = new HashSet<>(Arrays.asList(states));
    }

    public MultiState(Set<SingleState> states) {
        this.states = states;
    }

    @Override
    public Set<SingleState> getStates() {
        return states;
    }

    @Override
    public Set<Integer> getIdentifiers() {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }
        return identifiers;
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
}