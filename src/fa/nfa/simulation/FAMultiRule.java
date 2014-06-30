package fa.nfa.simulation;

import fa.MultiState;

public class FAMultiRule {

    private MultiState states;
    private Character character;
    private MultiState nextStates;

    public FAMultiRule(MultiState states, Character character, MultiState nextStates) {
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
    public boolean appliesTo(MultiState state, Character character) {
        return this.states.equals(state) && ((getCharacter() == null && character == null) || (getCharacter() == character));
    }

    /**
     * @return the nextState.
     */
    public MultiState follow() {
        return nextStates;
    }

    public MultiState getStates() {
        return states;
    }

    public Character getCharacter() {
        return character;
    }

    public MultiState getNextStates() {
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
