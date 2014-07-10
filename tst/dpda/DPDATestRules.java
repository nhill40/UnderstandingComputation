package dpda;

import java.util.ArrayList;
import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;

public class DPDATestRules {

    public static DPDARulebook DPDARULEBOOK = new DPDARulebook(Arrays.asList(
            new PDARule(STATE1, '(', STATE2, '$', Arrays.asList('b', '$')),
            new PDARule(STATE2, '(', STATE2, 'b', Arrays.asList('b', 'b')),
            new PDARule(STATE2, ')', STATE2, 'b', new ArrayList<Character>()),
            new PDARule(STATE2, null, STATE1, '$', Arrays.asList('$'))
    ));
}
