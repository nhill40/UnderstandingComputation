package fa;

import java.util.Set;

/**
 * A "multi" rule - a combination of 1:M current states, exactly 1 input character (for lookup purposes), and 1:M
 * nextStates (which states to move to next)
 */
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
        // TODO: is this going to be needed?  If so, needs to be refined to consider all states
        //return this.state == state && ((getCharacter() == null && character == null) || (getCharacter() == character));
        return false;
    }

    /**
     * @return the nextStates.
     */
    public Set<State> follow() {
        return nextStates;
    }

    @Override
    public String toString() {
        // This is intended as a testing/debugging convenience
        StringBuilder sb = new StringBuilder();
        sb.append(states);
        sb.append(" ---").append(getCharacter()).append("--> ");
        sb.append(nextStates);
        return sb.toString();
    }
}
