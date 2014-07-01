package fa;

/**
 * A multi rule defines a state transition by encapsulating a collection of states, a character, and a collection of
 * next states.
 */
public class FAMultiRule implements FARule {

    // TODO: shouldn't these be MultiStates?
    private State states;
    private Character character;
    private State nextStates;

    public FAMultiRule(State states, Character character, State nextStates) {
        this.states = states;
        this.character = character;
        this.nextStates = nextStates;
    }

    @Override
    public boolean appliesTo(State state, Character character) {
        return this.states.equals(state) && ((getCharacter() == null && character == null) || (getCharacter() == character));
    }

    @Override
    public State follow() {
        return nextStates;
    }

    public State getStates() {
        return states;
    }

    public Character getCharacter() {
        return character;
    }

    public void setStates(MultiState states) {
        this.states = states;
    }

    @Override
    public String toString() {
        // This is intended as a testing/debugging convenience
        StringBuilder sb = new StringBuilder();
        sb.append(states);
        sb.append(" ---").append(character).append("--> ");
        sb.append(nextStates);
        return sb.toString();
    }
}
