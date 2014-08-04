package dtm;

import org.junit.Test;

import java.util.LinkedList;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TMRuleTest {

    @Test
    public void test_appliesTo() {
        TMRule rule = new TMRule(STATE1, '0', STATE2, '1', Direction.RIGHT);
        assertTrue(rule.appliesTo(new TMConfiguration(STATE1,
                new Tape(new LinkedList<Character>(), '0', new LinkedList<Character>(), '_'))));
        assertFalse(rule.appliesTo(new TMConfiguration(STATE1,
                new Tape(new LinkedList<Character>(), '1', new LinkedList<Character>(), '_'))));
        assertFalse(rule.appliesTo(new TMConfiguration(STATE2,
                new Tape(new LinkedList<Character>(), '0', new LinkedList<Character>(), '_'))));
    }

    @Test
    public void test_follow() {
        TMRule rule = new TMRule(STATE1, '0', STATE2, '1', Direction.RIGHT);
        assertEquals("state=2, tape=#<Tape 1(_)>", rule.follow(new TMConfiguration(STATE1,
                new Tape(new LinkedList<Character>(), '0', new LinkedList<Character>(), '_'))).toString());
    }
}
