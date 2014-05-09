package fa;

/**
 * A "single" rule - a combination of exactly 1 current state, exactly 1 input character (for lookup purposes), and
 * exactly 1 nextState (what state to move to next)
 */
public class FASingleRule extends FARule {

    private State state;
    private State nextState;

    public FASingleRule(State state, Character character, State nextState) {
        super(character);
        this.state = state;
        this.nextState = nextState;
    }

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
    public boolean appliesTo(State state, Character character) {
        return this.state == state && ((getCharacter() == null && character == null) || (getCharacter() == character));
    }

    /**
     * @return the nextState.
     */
    public State follow() {
        return nextState;
    }
}
