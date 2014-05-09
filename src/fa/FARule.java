package fa;

/**
 * Superclass for all rule subtypes.  A rule is simply a combination of current state(s), an input character (for
 * lookup purposes) and nextState(s) (what state(s) to move to next).
 */
public abstract class FARule {
    private Character character;

    public FARule(Character character) {
        this.character = character;
    }

    public Character getCharacter() {
        return character;
    }
}
