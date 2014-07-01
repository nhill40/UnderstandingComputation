package fa;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public  class MultiState implements State {
    private Set<State> states;

    public MultiState(State... states) {
        this.states = new HashSet<>(Arrays.asList(states));
    }

    public MultiState(Set<State> states) {
        this.states = states;
    }

    @Override
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

    @Override
    public Set<Integer> getIdentifiers() {
        Set<Integer> identifiers = new TreeSet<>();
        for (State state : states) {
            identifiers.addAll(state.getIdentifiers());
        }
        return identifiers;
    }
}