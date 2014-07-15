package pda.npda;

import fa.State;
import pda.PDAConfiguration;
import pda.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * A wrapper that creates a just-in-time NPDA that allows us to execute String input against.  We can create one
 * NPDADesign:
 *      NPDADesign npdaDesign = new NPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), NPDARULEBOOK);
 *
 * And then repeatedly call this:
 *      npdaDesign.accepts("mystring");
 *
 */
public class NPDADesign {

    private State startState;
    private Character bottomCharacter;
    private List<State> acceptStates;
    private NPDARulebook rulebook;

    public NPDADesign(State startState, Character bottomCharacter, List<State> acceptStates, NPDARulebook rulebook) {
        this.startState = startState;
        this.bottomCharacter = bottomCharacter;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Bounces a string against a just-in-time created NPDA.
     * @param string the string to evaluate for acceptance.
     * @return whether or not the string was accepted by the JIT NPDA.
     */
    public boolean accepts(String string) {
        NPDA npda = toNPDA();
        npda.readString(string);
        return npda.accepting();
    }

    /**
     * Creates a just-in-time NPDA based on the provided start/accept states, bottom character, and rulebook.
     * @return the NPDA.
     */
    public NPDA toNPDA() {
        Stack startStack = new Stack(Arrays.asList(bottomCharacter));
        PDAConfiguration startConfiguration = new PDAConfiguration(startState, startStack);
        return new NPDA(new HashSet<>(Arrays.asList(startConfiguration)), acceptStates, rulebook);
    }
}
