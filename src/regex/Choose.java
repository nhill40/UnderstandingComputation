package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Choose extends Pattern {
    private List<Pattern> patterns;

    public Choose(Pattern first, Pattern second) {
        patterns = new LinkedList<Pattern>();
        patterns.add(first);
        patterns.add(second);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Pattern pattern : patterns) {
            sb.append(pattern.bracket(getPrecedence()));
            sb.append("|");
        }

        return sb.deleteCharAt(sb.length()-1).toString();
    }

    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    public NFADesign toNFADesign() {
        NFADesign firstNFADesign = patterns.get(0).toNFADesign();
        NFADesign secondNFADesign = patterns.get(1).toNFADesign();

        State startState = new State();
        List<State> acceptStates = new ArrayList<State>();
        acceptStates.addAll(firstNFADesign.getAcceptStates());
        acceptStates.addAll(secondNFADesign.getAcceptStates());

        List<FARule> rules = new ArrayList<FARule>();
        rules.addAll(firstNFADesign.getRulebook().getRules());
        rules.addAll(secondNFADesign.getRulebook().getRules());

        rules.add(new FARule(startState, '\0', firstNFADesign.getStartState()));
        rules.add(new FARule(startState, '\0', secondNFADesign.getStartState()));

        NFARulebook rulebook = new NFARulebook(rules);

        return new NFADesign(startState, acceptStates, rulebook);
    }
}
