package fa.nfa;

import fa.FARule;

import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.FATestStates.STATE5;
import static fa.FATestStates.STATE6;

public class NFATestRules {

    public static final NFARulebook NFA_RULEBOOK = new NFARulebook(Arrays.asList(
            new FARule(STATE1, 'a', STATE1), new FARule(STATE1, 'a', STATE2), new FARule(STATE1, null, STATE2),
            new FARule(STATE2, 'b', STATE3),
            new FARule(STATE3, null, STATE2), new FARule(STATE3, 'b', STATE1)));

    // a set of rules depicting a NFA that accepts strings where the 3rd to last character is 'b'
    public static final NFARulebook NFA_RULEBOOK2 = new NFARulebook(Arrays.asList(
            new FARule(STATE1, 'a', STATE1), new FARule(STATE1, 'b', STATE1), new FARule(STATE1, 'b', STATE2),
            new FARule(STATE2, 'a', STATE3), new FARule(STATE2, 'b', STATE3),
            new FARule(STATE3, 'a', STATE4), new FARule(STATE3, 'b', STATE4)));

    // a set of rules depicting a NFA that accepts strings of a length that is a multiple of 2 OR 3.
    // Note that this requires the use of "free moves" - represented by nulls ('\0' is char equivalent of null).
    public static final NFARulebook NFA_RULEBOOK3 = new NFARulebook(Arrays.asList(
            new FARule(STATE1, null, STATE2), new FARule(STATE1, null, STATE4),
            new FARule(STATE2, 'a', STATE3),
            new FARule(STATE3, 'a', STATE2),
            new FARule(STATE4, 'a', STATE5),
            new FARule(STATE5, 'a', STATE6),
            new FARule(STATE6, 'a', STATE4)));
}
