package fa.nfa;

import fa.FARule;
import fa.FASingleRule;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;

public class NFATestRules {

    public static final NFARulebook NFA_RULEBOOK = new NFARulebook(Arrays.asList(
            new FASingleRule(STATE1, 'a', STATE1), new FASingleRule(STATE1, 'a', STATE2), new FASingleRule(STATE1, null, STATE2),
            new FASingleRule(STATE2, 'b', STATE3),
            new FASingleRule(STATE3, null, STATE2), new FASingleRule(STATE3, 'b', STATE1)));
}
