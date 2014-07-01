package regex;

import fa.FASingleRule;
import fa.SingleState;
import fa.State;
import fa.nfa.NFADesign;
import fa.nfa.NFARulebook;

import java.util.Arrays;

/**
 * Example:  "" (empty string)
 * Only 1 state - serves as both the start state as well as the accept state.  Also only 1 rule - empty
 * character (moves from state 1 to state 1).  So, passed an empty string, this NFA does not budge - it stays in
 * its start state (which is also its accept state).
 * Given ANY non-empty input, the NFA will try to look up a rule, find none and will zap the current state
 * (current state will basically be 'undefined') - so does NOT accept.
 */
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

        State startState = new SingleState(1);
        return new NFADesign(startState,
               Arrays.asList(startState),
                new NFARulebook(Arrays.asList(new FASingleRule(startState, '\0', startState))));
    }
}
