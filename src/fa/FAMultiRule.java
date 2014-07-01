package fa;

public class FAMultiRule implements FARule {

    private State states;
    private Character character;
    private State nextStates;

    public FAMultiRule(State states, Character character, State nextStates) {
        this.states = states;
        this.character = character;
        this.nextStates = nextStates;
    }

    /**
     * Determines if this particular rule applies to the given state/character.
     * @param state the state to check for.
     * @param character the character to check for.
     * @return whether this rule applies to the given state/character.
     */
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

    public State getNextStates() {
        return nextStates;
    }

    public void setStates(MultiState states) {
        this.states = states;
    }

    public void setNextStates(MultiState nextStates) {
        this.nextStates = nextStates;
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
