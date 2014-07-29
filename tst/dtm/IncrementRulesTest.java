package dtm;

import fa.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static fa.FATestStates.STATE0;
import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertEquals;

public class IncrementRulesTest {

    @Test
    public void test() {
        DTMRulebook rulebook = new DTMRulebook(new ArrayList<TMRule>());
        rulebook.getRules().addAll(IncrementRules.getIncrementRules(STATE0, STATE1));
        rulebook.getRules().addAll(IncrementRules.getIncrementRules(STATE1, STATE2));
        rulebook.getRules().addAll(IncrementRules.getIncrementRules(STATE2, STATE3));
        assertEquals(18, rulebook.getRules().size());

        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');
        assertEquals("#<Tape 101(1)>", tape.toString());

        DTM dtm = new DTM(new TMConfiguration(STATE0, tape), new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        dtm.run();
        assertEquals("#<Tape 111(0)_>", dtm.getCurrentConfiguration().getTape().toString());
    }
}
