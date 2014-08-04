package pda;

import fa.State;

import java.util.Collections;
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
                && ((this.character == null && character == null) || (this.character == character));
    }

    /**
     * Return a newly constructed PDA Configuration built using this rule's specified nextState as well as a newly
     * constructed Stack based upon the configuration changes (the pop + push characters) specified by this rule.
     * @param configuration the PDA Configuration to use as a starting point for modifying the stack.
     * @return the newly constructed PDA Configuration, containing this rule's nextState as its current state and a
     *         modified stack based upon this rule's pop/push characters.
     */
    public PDAConfiguration follow(PDAConfiguration configuration) {
        return new PDAConfiguration(nextState, nextStack(configuration));
    }

    /**
     * A method that modifies a given PDA Configuration's stack by popping of its topmost character and then pushing
     * this rule's "push characters" on top of the stack.
     * @param configuration the configuration to pull the starting point stack from.
     * @return the modified stack.
     */
    public Stack nextStack(PDAConfiguration configuration) {
        // Pop the topmost item off the provided configuration's stack and capture that new stack as poppedStack
        Stack poppedStack = configuration.getStack().pop();

        // Push characters are stored in readability-friendly order, but need to be pushed on in reverse order.
        Collections.reverse(pushCharacters);
        // Push this rule's push characters to the top of the stack.
        for (Character pushCharacter : pushCharacters) {
            poppedStack = poppedStack.push(pushCharacter);
        }
        // Reverse again for readability.
        Collections.reverse(pushCharacters);

        return poppedStack;
    }
}
