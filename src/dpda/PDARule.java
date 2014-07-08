package dpda;

import fa.State;

import java.util.List;

/**
 * A PDA rule is a combination of current state, an input character (for lookup purposes), nextState (what state to move
 * to next), as well as a "pop character" (what character to remove from the top of the stack) and "push characters"
 * (what new characters to put on top of the stack).
 */
public class PDARule {
    private State state;
    private Character character;
    private State nextState;
    private Character popCharacter;
    private List<Character> pushCharacters;

    public PDARule(State state, Character character, State nextState, Character popCharacter, List<Character> pushCharacters) {
        this.state = state;
        this.character = character;
        this.nextState = nextState;
        this.popCharacter = popCharacter;
        this.pushCharacters = pushCharacters;
    }

    /**
     * Determines if this particular rule applies to the given PDA Config/character.
     * @param configuration the PDA configuration to use to pull the current state and top stack character from.
     * @param character the character to check for.
     * @return whether this rule applies to the given PDA config/character.
     */
    public boolean appliesTo(PDAConfiguration configuration, Character character) {
        return this.state.equals(configuration.getState())
                && (this.popCharacter.equals(configuration.getStack().top()))
                && this.character.equals(character);
    }
}
