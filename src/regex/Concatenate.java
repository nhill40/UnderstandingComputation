package regex;

import fa.FASingleRule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Examples:  "ab", "a(|b)", "abc"
 * Combines two regex patterns and only accepts if it finds those 2 patterns mashed up against one another.
 * This could be simple as two character literals (e.g. "ab") or a concatenation of 2 more complicated regex
 * patterns (e.g. "a(|b)" < a character literal mashed together with a "Choose").  You can also mash together more than
 * two patterns, like "abc".  This is treated like "a(bc)" ("a" concatenated with the concatenation of "bc").
 * For implementation, we build an NFA taking the start state from the first pattern and any accept state(s) from the
 * second pattern.  The rules include all rules from both the first and second patterns as well as "free move" rules to
 * connect the first patterns old accept state(s) to the start state of the second pattern.
 */
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

        List<FASingleRule> rules = new ArrayList<FASingleRule>();
        rules.addAll(firstNFADesign.getRulebook().getRules());
        rules.addAll(secondNFADesign.getRulebook().getRules());

        for (State state : firstNFADesign.getAcceptStates()) {
            rules.add(new FASingleRule(state, null, secondNFADesign.getStartState()));
        }

        NFARulebook rulebook = new NFARulebook(rules);
        return new NFADesign(startState, acceptStates, rulebook);
    }
}
