package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Example:  "a|b"
 * Accepts one or the other.  Implementation is relatively simple:  we create a new start state that is connected with
 * "free move" rules to the old start states of the first and second patterns.  Accept states are simply the superset of
 * the accept state(s) from the first and second patterns.
 */
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

        State startState = new State(1);
        List<State> acceptStates = new ArrayList<State>();
        acceptStates.addAll(firstNFADesign.getAcceptStates());
        acceptStates.addAll(secondNFADesign.getAcceptStates());

        List<FARule> rules = new ArrayList<FARule>();
        rules.addAll(firstNFADesign.getRulebook().getRules());
        rules.addAll(secondNFADesign.getRulebook().getRules());

        rules.add(new FARule(startState, null, firstNFADesign.getStartState()));
        rules.add(new FARule(startState, null, secondNFADesign.getStartState()));

        NFARulebook rulebook = new NFARulebook(rules);

        return new NFADesign(startState, acceptStates, rulebook);
    }
}
