package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.Arrays;

public class Literal extends Pattern {

    private Character character;

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
        State startState = new State();
        State acceptState = new State();
        return new NFADesign(startState,
                Arrays.asList(acceptState),
                new NFARulebook(Arrays.asList(
                        new FARule(startState, character, acceptState))));
    }
}
