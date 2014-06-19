package fa;

import java.util.Set;

// TODO: the only place this is being used in within the NFASimulation (which is also the only place it probably SHOULD be used!)  Move to an inner class within that class?
public class FAMultiRule extends FARule {
    private Set<State> states;
    private Set<State> nextStates;

    public FAMultiRule(Set<State> states, Character character, Set<State> nextStates) {
        super(character);
        this.states = states;
        this.nextStates = nextStates;
    }

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
    public boolean appliesTo(State state, Character character) {
        // TODO: provide real implementation
        //return this.state.equals(state) && ((getCharacter() == null && character == null) || (getCharacter() == character));
        return true;
    }

    /**
     * @return the nextState.
     */
    public Set<State> follow() {
        return nextStates;
    }

    @Override
    public String toString() {
        // This is intended as a testing/debugging convenience
        StringBuilder sb = new StringBuilder();
        // TODO: need to validate what this looks like printed
        sb.append(states);
        sb.append(" ---").append(getCharacter()).append("--> ");
        sb.append(nextStates);
        return sb.toString();
    }
}
