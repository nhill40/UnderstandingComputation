package fa;

/**
 * Superclass for all rule subtypes.  A rule is simply a combination of current state(s), an input character (for
 * lookup purposes) and nextState(s) (what state(s) to move to next).
 */
public class FARule {
    private State state;
    private Character character;
    private State nextState;

    public FARule(State state, Character character, State nextState) {
        this.state = state;
        this.character = character;
        this.nextState = nextState;
    }

    public Character getCharacter() {
        return character;
    }

    public State getState() {
        return state;
    }

    public State getNextState() {
        return nextState;
    }

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
    public boolean appliesTo(State state, Character character) {
        return this.state.equals(state) && ((getCharacter() == null && character == null) || (getCharacter() == character));
    }

    /**
     * @return the nextState.
     */
    public State follow() {
        return nextState;
    }

    @Override
    public String toString() {
        // This is intended as a testing/debugging convenience
        StringBuilder sb = new StringBuilder();
        sb.append(state);
        sb.append(" ---").append(character).append("--> ");
        sb.append(nextState);
        return sb.toString();
    }
}
