package fa.dfa;

import fa.FARule;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;

public class DFATestRules {

    // A set of rules depicting a DFA that accepts strings containing the sequence 'ab'
    public static final DFARulebook DFA_RULEBOOOK = new DFARulebook(Arrays.asList(
            new FARule(STATE1, 'a', STATE2), new FARule(STATE1, 'b', STATE1),
            new FARule(STATE2, 'a', STATE2), new FARule(STATE2, 'b', STATE3),
            new FARule(STATE3, 'a', STATE3), new FARule(STATE3, 'b', STATE3)));
}
