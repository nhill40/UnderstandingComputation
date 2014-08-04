package fa.dfa;

import fa.State;

import java.util.Set;

/**
 * A wrapper that creates a just-in-time DFA that allows us to execute String input against.  We can create one
 * DFADesign:
 *      DFADesign dfaDesign = new DFADesign(STATE1, Arrays.asList(STATE3), DFA_RULEBOOK);
 *
 * And then repeatedly call this:
 *      dfaDesign.accepts("mystring");
 *
 */
public class DFADesign {
    private final State startState;
    private final Set<State> acceptStates;
    private final DFARulebook rulebook;

    public DFADesign(State startState, Set<State> acceptStates, DFARulebook rulebook) {
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Creates a just-in-time DFA based on the provided start/accept states and rulebook.
     * @return the DFA.
     */
    public DFA toDFA() {
        return new DFA(startState, acceptStates, rulebook);
    }

    /**
     * Bounces a string against a just-in-time created DFA.
     * @param string the string to evaluate for acceptance.
     * @return whether or not the string was accepted.
     */
    public boolean accepts(String string) {
        DFA dfa = toDFA();
        dfa.readString(string);
        return dfa.accepting();
    }
}
