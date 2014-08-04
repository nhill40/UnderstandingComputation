package pda.dpda;

import fa.State;
import org.junit.Test;
import pda.PDARule;

import java.util.ArrayList;
import java.util.Arrays;

import static fa.FATestStates.STATE1;
import static fa.FATestStates.STATE2;
import static fa.FATestStates.STATE3;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pda.dpda.DPDATestRules.DPDARULEBOOK;

public class DPDADesignTest {

    @Test
    public void test_accepts() {
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);
        assertTrue(dpdaDesign.accepts("(((((((((())))))))))"));
        assertTrue(dpdaDesign.accepts("()(())((()))(()(()))"));
        assertFalse(dpdaDesign.accepts("(()(()(()()(()()))()"));
    }

    @Test
    public void test_acceptsBalancedLetters() {
        DPDARulebook rulebook = new DPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE2, '$', Arrays.asList('a', '$')),
                new PDARule(STATE1, 'b', STATE2, '$', Arrays.asList('b', '$')),
                new PDARule(STATE2, 'a', STATE2, 'a', Arrays.asList('a', 'a')),
                new PDARule(STATE2, 'b', STATE2, 'b', Arrays.asList('b', 'b')),
                new PDARule(STATE2, 'a', STATE2, 'b', new ArrayList<Character>()),
                new PDARule(STATE2, 'b', STATE2, 'a', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE1, '$', Arrays.asList('$'))
        ));
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), rulebook);
        assertTrue(dpdaDesign.accepts("ababab"));
        assertTrue(dpdaDesign.accepts("bbbaaaab"));
    }

    @Test
    public void test_acceptsBalancedLetters2() {
        // This test proves we are not really leveraging the full power of a stack yet by demonstrating the above
        // problem could be solved by adding 1 extra state and using any arbitrary character (in this case, 'c') to do
        // our counting for use (i.e. all we are using the stack for in these 2 examples is a crude counting mechanism)
        DPDARulebook rulebook = new DPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE2, '$', Arrays.asList('c', '$')),
                new PDARule(STATE2, 'a', STATE2, 'c', Arrays.asList('c', 'c')),
                new PDARule(STATE2, 'b', STATE2, 'c', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE1, '$', Arrays.asList('$')),
                new PDARule(STATE1, 'b', STATE3, '$', Arrays.asList('c', '$')),
                new PDARule(STATE3, 'a', STATE3, 'c', new ArrayList<Character>()),
                new PDARule(STATE3, 'b', STATE3, 'c', Arrays.asList('c', 'c')),
                new PDARule(STATE3, null, STATE1, '$', Arrays.asList('$'))
        ));
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), rulebook);
        assertTrue(dpdaDesign.accepts("ababab"));
        assertTrue(dpdaDesign.accepts("bbbaaaab"));
    }

    @Test
    public void test_palindromes_kinda() {
        // This is "kinda" because there is a pretty big cheat - it requires us to use an arbitrary "marker" character
        // (in this example 'm') to indicate to the DPDA at what point it should begin unwinding characters from the
        // stack.
        DPDARulebook rulebook = new DPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE1, '$', Arrays.asList('a', '$')),
                new PDARule(STATE1, 'a', STATE1, 'a', Arrays.asList('a', 'a')),
                new PDARule(STATE1, 'a', STATE1, 'b', Arrays.asList('a', 'b')),
                new PDARule(STATE1, 'b', STATE1, '$', Arrays.asList('b', '$')),
                new PDARule(STATE1, 'b', STATE1, 'a', Arrays.asList('b', 'a')),
                new PDARule(STATE1, 'b', STATE1, 'b', Arrays.asList('b', 'b')),
                new PDARule(STATE1, 'm', STATE2, '$', Arrays.asList('$')),
                new PDARule(STATE1, 'm', STATE2, 'a', Arrays.asList('a')),
                new PDARule(STATE1, 'm', STATE2, 'b', Arrays.asList('b')),
                new PDARule(STATE2, 'a', STATE2, 'a', new ArrayList<Character>()),
                new PDARule(STATE2, 'b', STATE2, 'b', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE3, '$', Arrays.asList('$'))
        ));
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        assertTrue(dpdaDesign.accepts("abmba"));
        assertTrue(dpdaDesign.accepts("babbamabbab"));
        assertFalse(dpdaDesign.accepts("abmb"));
        assertFalse(dpdaDesign.accepts("baambaa"));
    }

    @Test
    public void test_stuck() {
        DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);

        // No rule applies for a closing paren with no corresponding open paren!!
        assertFalse(dpdaDesign.accepts("())"));
    }
}
