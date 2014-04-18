package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.Arrays;

public class Literal extends Pattern {

    private char character;

    public Literal(char character) {
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
        State literalState1 = new State();
        return new NFADesign(literalState1,
                Arrays.asList(literalState1),
                new NFARulebook(Arrays.asList(
                        new FARule(literalState1, character, literalState1))));
    }
}
