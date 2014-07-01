package fa.dfa;

import fa.FARule;
import fa.FASingleRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;

public class DFATestRules {

    private static List<FARule> rules = new ArrayList<>();
    static {
        rules.addAll(Arrays.asList(
                new FASingleRule(STATE1, 'a', STATE2), new FASingleRule(STATE1, 'b', STATE1),
                new FASingleRule(STATE2, 'a', STATE2), new FASingleRule(STATE2, 'b', STATE3),
                new FASingleRule(STATE3, 'a', STATE3), new FASingleRule(STATE3, 'b', STATE3)));
    }

    // A set of rules depicting a DFA that accepts strings containing the sequence 'ab'
    public static final DFARulebook DFA_RULEBOOOK = new DFARulebook(rules);
}
