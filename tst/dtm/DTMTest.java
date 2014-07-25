package dtm;

import fa.State;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static fa.FATestStates.STATE4;
import static fa.FATestStates.STATE5;
import static fa.FATestStates.STATE6;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DTMTest {

    private static final DTMRulebook RULEBOOK = new DTMRulebook(Arrays.asList(
            new TMRule(STATE1, '0', STATE2, '1', Direction.RIGHT),
            new TMRule(STATE1, '1', STATE1, '0', Direction.LEFT),
            new TMRule(STATE1, '_', STATE2, '1', Direction.RIGHT),
            new TMRule(STATE2, '0', STATE2, '0', Direction.RIGHT),
            new TMRule(STATE2, '1', STATE2, '1', Direction.RIGHT),
            new TMRule(STATE2, '_', STATE3, '_', Direction.LEFT)
    ));

    @Test
    public void test_dtm() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');

        DTM dtm = new DTM(new TMConfiguration(STATE1, tape), new ArrayList<State>(Arrays.asList(STATE3)), RULEBOOK);
        assertEquals("state=1, tape=#<Tape 101(1)>", dtm.getCurrentConfiguration().toString());
        assertFalse(dtm.accepting());

        dtm.step();
        assertEquals("state=1, tape=#<Tape 10(1)0>", dtm.getCurrentConfiguration().toString());
        assertFalse(dtm.accepting());

        dtm.run();
        assertEquals("state=3, tape=#<Tape 110(0)_>", dtm.getCurrentConfiguration().toString());
        assertTrue(dtm.accepting());
    }

    @Test
    public void test_stuck() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '2', '1')), '1',  new LinkedList<Character>(), '_');
        DTM dtm = new DTM(new TMConfiguration(STATE1, tape), new ArrayList<State>(Arrays.asList(STATE3)), RULEBOOK);
        dtm.run();
        assertEquals("state=1, tape=#<Tape 1(2)00>", dtm.getCurrentConfiguration().toString());
        assertFalse(dtm.accepting());
        assertTrue(dtm.stuck());
    }

    @Test
    public void test_balancedStringIdentification() {
        DTMRulebook rulebook = new DTMRulebook(Arrays.asList(
                // STATE1 = Scan left-to-right looking for a's
                new TMRule(STATE1, 'X', STATE1, 'X', Direction.RIGHT), // skip X's
                new TMRule(STATE1, 'a', STATE2, 'X', Direction.RIGHT), // X out a, go to STATE2
                new TMRule(STATE1, '_', STATE6, '_', Direction.LEFT), // no more a's, go to STATE6 (accept)

                // STATE2 = Scan left-to-right looking for b's
                new TMRule(STATE2, 'a', STATE2, 'a', Direction.RIGHT), // skip a's
                new TMRule(STATE2, 'X', STATE2, 'X', Direction.RIGHT), // skip X's
                new TMRule(STATE2, 'b', STATE3, 'X', Direction.RIGHT), // X out b, go to STATE3

                // STATE3 = Scan left-to-right looking for c's
                new TMRule(STATE3, 'b', STATE3, 'b', Direction.RIGHT), // skip b's
                new TMRule(STATE3, 'X', STATE3, 'X', Direction.RIGHT), // skip X's
                new TMRule(STATE3, 'c', STATE4, 'X', Direction.RIGHT), // X out b, go to STATE4

                // STATE4 = scan left-to-right looking for end of string
                new TMRule(STATE4, 'c', STATE4, 'c', Direction.RIGHT), // skip c's
                new TMRule(STATE4, '_', STATE5, '_', Direction.LEFT), // found blank, go to STATE5

                // STATE5 = scan right-to-left looking for beginnin of string
                new TMRule(STATE5, 'a', STATE5, 'a', Direction.LEFT), // skip a's
                new TMRule(STATE5, 'b', STATE5, 'b', Direction.LEFT), // skip b's
                new TMRule(STATE5, 'c', STATE5, 'c', Direction.LEFT), // skip c's
                new TMRule(STATE5, 'X', STATE5, 'X', Direction.LEFT), // skip X's
                new TMRule(STATE5, '_', STATE1, '_', Direction.RIGHT) // found blank, go back to STATE1
        ));

        Tape tape = new Tape(new LinkedList<Character>(), 'a',
                new LinkedList<>(Arrays.asList('a', 'a', 'b', 'b', 'b', 'c', 'c', 'c')), '_');

        DTM dtm = new DTM(new TMConfiguration(STATE1, tape), new ArrayList<State>(Arrays.asList(STATE6)), rulebook);

        for (int i = 0; i < 10; i++) {
            dtm.step();
        }
        assertEquals("state=5, tape=#<Tape XaaXbbXc(c)_>", dtm.getCurrentConfiguration().toString());

        for (int i = 0; i < 25; i++) {
            dtm.step();
        }
        assertEquals("state=5, tape=#<Tape _XXa(X)XbXXc_>", dtm.getCurrentConfiguration().toString());

        dtm.run();
        assertEquals("state=6, tape=#<Tape _XXXXXXXX(X)_>", dtm.getCurrentConfiguration().toString());
    }
}
