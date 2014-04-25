package regex;

import fa.FARule;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.Arrays;

public class Empty extends Pattern {

    @Override
    public String toString() {
        return "";
    }

    @Override
    public int getPrecedence() {
        return 3;
    }

    @Override
    public NFADesign toNFADesign() {
        State startState = new State();

        // Start state is 1.  Accept state is also 1.  Only 1 rule - empty character (moves from state 1 to state 1).
        // So, passed an empty string, this NFA does not budge - stays in state 1 which is an accept state.
        // Given ANY non-empty input, the NFA will try to look up a rule, find none and will zap the current state
        // (current state will basically be 'undefined') - so does NOT accept.
        return new NFADesign(startState,
               Arrays.asList(startState),
                new NFARulebook(Arrays.asList(new FARule(startState, '\0', startState))));
    }
}
