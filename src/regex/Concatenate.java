package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Concatenate extends Pattern {
    private List<Pattern> patterns;

    public Concatenate(Pattern first, Pattern second) {
        patterns = new LinkedList<Pattern>();
        patterns.add(first);
        patterns.add(second);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pattern pattern : patterns) {
            sb.append(pattern.bracket(getPrecedence()));
        }

        return sb.toString();
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    public NFADesign toNFADesign() {
        NFADesign firstNFADesign = patterns.get(0).toNFADesign();
        NFADesign secondNFADesign = patterns.get(1).toNFADesign();

        State startState = firstNFADesign.getStartState();
        List<State> acceptStates = secondNFADesign.getAcceptStates();

        List<FARule> rules = new ArrayList<FARule>();
        rules.addAll(firstNFADesign.getRulebook().getRules());
        rules.addAll(secondNFADesign.getRulebook().getRules());

        for (State state : firstNFADesign.getAcceptStates()) {
            rules.add(new FARule(state, null, secondNFADesign.getStartState()));
        }

        NFARulebook rulebook = new NFARulebook(rules);
        return new NFADesign(startState, acceptStates, rulebook);
    }
}
