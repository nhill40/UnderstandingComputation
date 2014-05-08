package fa;

/**
 * A rule - simply a combination of current state and input character (for lookup purposes) and nextState (what state
 * to move to next)
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

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
    public boolean appliesTo(State state, Character character) {
        return this.state == state && ((this.character == null && character == null) || (this.character == character));
    }

    /**
     * @return the nextState.
     */
    public State follow() {
        return nextState;
    }

    public Character getCharacter() {
        return character;
    }
}
