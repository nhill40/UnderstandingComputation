package fa;

public interface FARule {

    public boolean appliesTo(State state, Character character);
    public State follow();
}
