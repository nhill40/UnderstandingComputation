package fa;

/**
 * A single rule defines a state transition by encapsulating a single state, a character, and a single next state.
 */
public class FASingleRule implements FARule {

    // TODO: shouldn't these be single states?
    private State state;
    private Character character;
    private State nextState;

    public FASingleRule(State state, Character character, State nextState) {
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

    @Override
    public boolean appliesTo(State state, Character character) {
        return this.state.equals(state) && ((getCharacter() == null && character == null) || (getCharacter() == character));
    }

    @Override
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
