package fa.nfa.simulation;

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
