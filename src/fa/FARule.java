package fa;

/**
 * Superclass for all rule subtypes.  A rule is simply a combination of current state(s), an input character (for
 * lookup purposes) and nextState(s) (what state(s) to move to next).
 */
public interface FARule {

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
    public boolean appliesTo(State state, Character character);

    /**
     * @return the nextState.
     */
    public State follow();
}
