package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.List;

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
        NFADesign nfaDesign = pattern.toNFADesign();

        State startState = new State();
        List<State> acceptStates = new ArrayList<State>();
        acceptStates.addAll(nfaDesign.getAcceptStates());
        acceptStates.add(startState);

        List<FARule> rules = new ArrayList<FARule>();
        rules.addAll(nfaDesign.getRulebook().getRules());

        for (State acceptState : nfaDesign.getAcceptStates()) {
            rules.add(new FARule(acceptState, null, nfaDesign.getStartState()));
        }
        rules.add(new FARule(startState, null, nfaDesign.getStartState()));
        rules.add(new FARule(startState, '\0', startState));

        NFARulebook rulebook = new NFARulebook(rules);

        return new NFADesign(startState, acceptStates, rulebook);
    }
}
