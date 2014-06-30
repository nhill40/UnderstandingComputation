package fa.dfa.alternate;

import fa.nfa.simulation.MultiState;

import java.util.Set;

public class DFADesignAlt {

    private final MultiState startState;
    private final Set<MultiState> acceptStates;
    private final DFARulebookAlt rulebook;

    public DFADesignAlt(MultiState startState, Set<MultiState> acceptStates, DFARulebookAlt rulebook) {
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Creates a just-in-time DFA based on the provided start/accept states and rulebook.
     * @return the DFA.
     */
    public DFAAlt toDFA() {
        return new DFAAlt(startState, acceptStates, rulebook);
    }

    /**
     * Bounces a string against a just-in-time created DFA.
     * @param string the string to evaluate for acceptance.
     * @return whether or not the string was accepted.
     */
    public boolean accepts(String string) {
        DFAAlt dfa = toDFA();
        dfa.readString(string);
        return dfa.accepting();
    }
}
