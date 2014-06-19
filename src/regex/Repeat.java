package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.ArrayList;
import java.util.List;

/**
 * Example:  "a*", "(a|b)*"
 * Looks for repeating patterns.  In regex terms, "*" means "zero or more occurrences" which makes implementation a
 * little tricky because we also need to match on an empty string.  To achieve this, we create a new start state which
 * also serves as an accept state.  We also build a rule to connect this new start state to itself upon the input of an
 * empty string.  This all serves to satisfy the need to accept on an empty string - to be able to also match on the
 * pattern itself, so we add in all the rules from the pattern and create a "free move" rule to connect this new start
 * state to the old start state of the pattern.  Lastly, we also need to accept many occurrences of the pattern - to do
 * this, we connect any accept state(s) from the pattern back its own original start state using "free move" rules (this
 * essentially allows the pattern to start over upon reaching one of its accept state(s)).
 */
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

        State startState = new State(1);
        List<State> acceptStates = new ArrayList<>();
        acceptStates.addAll(nfaDesign.getAcceptStates());
        acceptStates.add(startState);

        List<FARule> rules = new ArrayList<>();
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
