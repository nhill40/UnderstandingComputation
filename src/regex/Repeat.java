package regex;

import fa.nfa.NFADesign;

public class Repeat extends Pattern {
    private Pattern pattern;

    public Repeat(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public String toString() {
        return pattern.bracket(getPrecedence()) + "*";
    }

    @Override
    public int getPrecedence() {
        return 2;
    }

    @Override
    public NFADesign toNFADesign() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
