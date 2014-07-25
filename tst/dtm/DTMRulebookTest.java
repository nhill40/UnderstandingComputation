package dtm;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertEquals;

public class DTMRulebookTest {

    @Test
    public void test_nextConfiguration() {
        Tape tape = new Tape(new LinkedList<>(Arrays.asList('1', '0', '1')), '1',  new LinkedList<Character>(), '_');

        DTMRulebook rulebook = new DTMRulebook(Arrays.asList(
                new TMRule(STATE1, '0', STATE2, '1', Direction.RIGHT),
                new TMRule(STATE1, '1', STATE1, '0', Direction.LEFT),
                new TMRule(STATE1, '_', STATE2, '1', Direction.RIGHT),
                new TMRule(STATE2, '0', STATE2, '0', Direction.RIGHT),
                new TMRule(STATE2, '1', STATE2, '1', Direction.RIGHT),
                new TMRule(STATE2, '_', STATE3, '_', Direction.LEFT)
        ));

        TMConfiguration configuration = new TMConfiguration(STATE1, tape);
        assertEquals("state=1, tape=#<Tape 101(1)>", configuration.toString());

        configuration = rulebook.nextConfiguration(configuration);
        assertEquals("state=1, tape=#<Tape 10(1)0>", configuration.toString());

        configuration = rulebook.nextConfiguration(configuration);
        assertEquals("state=1, tape=#<Tape 1(0)00>", configuration.toString());

        configuration = rulebook.nextConfiguration(configuration);
        assertEquals("state=2, tape=#<Tape 11(0)0>", configuration.toString());
    }
}
