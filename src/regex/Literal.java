package regex;

import fa.FASingleRule;
import fa.SingleState;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.Arrays;

/**
 * Example:  "a"
 * Simple implementation:  just two states - a start state and accept state.  One rule to join those two states
 * together via an input character (initialized in constructor).
 */
public class Literal extends Pattern {

    private final Character character;

    public Literal(Character character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return new String(new char[] {character});
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public NFADesign toNFADesign() {
        State startState = new SingleState(1);
        State acceptState = new SingleState(2);
        return new NFADesign(startState,
                Arrays.asList(acceptState),
                new NFARulebook(Arrays.asList(
                        new FASingleRule(startState, character, acceptState))));
    }
}
