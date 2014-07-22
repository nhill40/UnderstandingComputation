package pda.npda;

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

public class NPDADesignTest {

    @Test
    public void test_accepts() {
        NPDARulebook rulebook = new NPDARulebook(Arrays.asList(
                new PDARule(STATE1, 'a', STATE1, '$', Arrays.asList('a', '$')),
                new PDARule(STATE1, 'a', STATE1, 'a', Arrays.asList('a', 'a')),
                new PDARule(STATE1, 'a', STATE1, 'b', Arrays.asList('a', 'b')),
                new PDARule(STATE1, 'b', STATE1, '$', Arrays.asList('b', '$')),
                new PDARule(STATE1, 'b', STATE1, 'a', Arrays.asList('b', 'a')),
                new PDARule(STATE1, 'b', STATE1, 'b', Arrays.asList('b', 'b')),
                new PDARule(STATE1, null, STATE2, '$', Arrays.asList('$')),
                new PDARule(STATE1, null, STATE2, 'a', Arrays.asList('a')),
                new PDARule(STATE1, null, STATE2, 'b', Arrays.asList('b')),
                new PDARule(STATE2, 'a', STATE2, 'a', new ArrayList<Character>()),
                new PDARule(STATE2, 'b', STATE2, 'b', new ArrayList<Character>()),
                new PDARule(STATE2, null, STATE3, '$', Arrays.asList('$'))
        ));
        NPDADesign npdaDesign = new NPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        assertTrue(npdaDesign.accepts("abba"));
        assertTrue(npdaDesign.accepts("babbaabbab"));
        assertFalse(npdaDesign.accepts("abb"));
        assertFalse(npdaDesign.accepts("baabaa"));
    }

    @Test
    public void test_parser() {
        NPDARulebook rulebook = new NPDARulebook(Arrays.asList(

                // This is a NPDA implementation of what is known as a "Context Free Configuration" (CFG)
                // The natural language explanations (e.g. "A statement can be a while or assignment") can be thought
                // of as the CFG.  Note this is a very limited grammar to keep it simple for demonstration purposes
                // (e.g. in practical programming languages, statements can be many more things than just whiles or
                // assignments, but for demonstration purposes this gets the point across)

                // SYMBOLS:
                // S = statement
                // W = while
                // A = assign
                // E = expression
                // L = less-than

                // TOKENS:
                // v = variable
                // w = while
                // n = number
                // *, =, <, (, ), {, } (self-explanatory)

                // Gets the flow started.  We know we are looking for a valid statement, so start with that:
                new PDARule(STATE1, null, STATE2, '$', Arrays.asList('S', '$')),

                // A statement can be a while or assignment:
                new PDARule(STATE2, null, STATE2, 'S', Arrays.asList('W')),
                new PDARule(STATE2, null, STATE2, 'S', Arrays.asList('A')),

                // A while looks like "w ( expression ) { statement }":
                new PDARule(STATE2, null, STATE2, 'W', Arrays.asList('w', '(', 'E', ')', '{', 'S', '}')),

                // An assignment looks like "v = expression":
                new PDARule(STATE2, null, STATE2, 'A', Arrays.asList('v', '=', 'E')),

                // An expression can expand to a less-than:
                new PDARule(STATE2, null, STATE2, 'E', Arrays.asList('L')),

                // A less-than can expand to "multiply < less-than" or just multiply:
                new PDARule(STATE2, null, STATE2, 'L', Arrays.asList('M', '<', 'L')),
                new PDARule(STATE2, null, STATE2, 'L', Arrays.asList('M')),

                // A multiply can expand to "term * multiply" or just term:
                new PDARule(STATE2, null, STATE2, 'M', Arrays.asList('T', '*', 'M')),
                new PDARule(STATE2, null, STATE2, 'M', Arrays.asList('T')),

                // A term can look like "n" (a number) or "v" (a variable):
                new PDARule(STATE2, null, STATE2, 'T', Arrays.asList('n')),
                new PDARule(STATE2, null, STATE2, 'T', Arrays.asList('v')),

                // The above rules BUILD the stack to tokens, the following will allow the tokens being read in to
                // CONSUME the stack...
                new PDARule(STATE2, 'w', STATE2, 'w', new ArrayList<Character>()),
                new PDARule(STATE2, 'v', STATE2, 'v', new ArrayList<Character>()),
                new PDARule(STATE2, 'n', STATE2, 'n', new ArrayList<Character>()),
                new PDARule(STATE2, '*', STATE2, '*', new ArrayList<Character>()),
                new PDARule(STATE2, '=', STATE2, '=', new ArrayList<Character>()),
                new PDARule(STATE2, '<', STATE2, '<', new ArrayList<Character>()),
                new PDARule(STATE2, '(', STATE2, '(', new ArrayList<Character>()),
                new PDARule(STATE2, ')', STATE2, ')', new ArrayList<Character>()),
                new PDARule(STATE2, '{', STATE2, '{', new ArrayList<Character>()),
                new PDARule(STATE2, '}', STATE2, '}', new ArrayList<Character>()),

                // And, finally, a way to stop if/when we reach the bottom of the stack:
                new PDARule(STATE2, null, STATE3, '$', Arrays.asList('$'))
        ));

        NPDADesign npdaDesign = new NPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE3)), rulebook);
        assertTrue(npdaDesign.accepts("w(v<n){v=v*n}"));
    }
}
