package fa;

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
        sb.append(" ---").append(getCharacter()).append("--> ");
        sb.append(nextState);
        return sb.toString();
    }
}
