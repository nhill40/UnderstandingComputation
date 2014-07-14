package pda.dpda;

import fa.State;
import pda.PDAConfiguration;
import pda.Stack;

import java.util.Arrays;
import java.util.List;

/**
 * A wrapper that creates a just-in-time DPDA that allows us to execute String input against.  We can create one
 * DPDADesign:
 *      DPDADesign dpdaDesign = new DPDADesign(STATE1, '$', new ArrayList<State>(Arrays.asList(STATE1)), DPDARULEBOOK);
 *
 * And then repeatedly call this:
 *      dpdaDesign.accepts("mystring");
 *
 */
public class DPDADesign {
    private State startState;
    private Character bottomCharacter;
    private List<State> acceptStates;
    private DPDARulebook rulebook;

    public DPDADesign(State startState, Character bottomCharacter, List<State> acceptStates, DPDARulebook rulebook) {
        this.startState = startState;
        this.bottomCharacter = bottomCharacter;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Bounces a string against a just-in-time created DPDA.
     * @param string the string to evaluate for acceptance.
     * @return whether or not the string was accepted by the JIT DPDA.
     */
    public boolean accepts(String string) {
        DPDA dpda = toDPDA();
        dpda.readString(string);
        return dpda.accepting();
    }

    /**
     * Creates a just-in-time DPDA based on the provided start/accept states, bottom character, and rulebook.
     * @return the DPDA.
     */
    public DPDA toDPDA() {
        Stack startStack = new Stack(Arrays.asList(bottomCharacter));
        PDAConfiguration startConfiguration = new PDAConfiguration(startState, startStack);
        return new DPDA(startConfiguration, acceptStates, rulebook);
    }
}
