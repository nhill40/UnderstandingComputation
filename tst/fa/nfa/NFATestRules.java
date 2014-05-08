package fa.nfa;

import fa.FARule;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;

public class NFATestRules {

    public static final NFARulebook NFA_RULEBOOK = new NFARulebook(Arrays.asList(
            new FARule(STATE1, 'a', STATE1), new FARule(STATE1, 'a', STATE2), new FARule(STATE1, null, STATE2),
            new FARule(STATE2, 'b', STATE3),
            new FARule(STATE3, null, STATE2), new FARule(STATE3, 'b', STATE1)));
}
