package fa.nfa;

import fa.SingleState;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A wrapper that creates a just-in-time NFA that allows us to execute String input against.  We can create one NFADesign:
 *      NFADesign nfaDesign = new DFADesign(Arrays.asList(1), Arrays.asList(4), rulebook);
 *
 * And then repeatedly call this:
 *      nfaDesign.accepts("mystring");
 *
 */

public class NFADesign {
    private final SingleState startState;
    private final List<SingleState> acceptStates;
    private final NFARulebook rulebook;

    public NFADesign(SingleState startState, List<SingleState> acceptStates, NFARulebook rulebook) {
        this.startState = startState;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    /**
     * Creates a just-in-time NFA based on the start/accept states and rulebook.
     * @return the NFA.
     */
    public NFA toNFA() {
        Set<SingleState> currentStates = new HashSet<>();
        currentStates.add(startState);
        return new NFA(currentStates, acceptStates, rulebook);
    }

    /**
     * Creates a just-in-time NFA based on the provided current states, the accept states, and rulebook.
     * @return the NFA.
     */
    public NFA toNFA(Set<SingleState> currentStates) {
        return new NFA(currentStates, acceptStates, rulebook);
    }

    /**
     * Creates a just-in-time NFA based on the provided current state, the accept states, and rulebook.
     * @return the NFA.
     */
    public NFA toNFA(SingleState currentState) {
        return new NFA(new HashSet<>(Arrays.asList(currentState)), acceptStates, rulebook);
    }

    /**
     * Bounces a string against a just-in-time created NFA.
     * @param string the string to evaluate for acceptance.
     * @return whether or not the string was accepted.
     */
    public boolean accepts(String string) {
        NFA nfa = toNFA();
        nfa.readString(string);
        return nfa.accepting();
    }

    public SingleState getStartState() {
        return startState;
    }

    public List<SingleState> getAcceptStates() {
        return acceptStates;
    }

    public NFARulebook getRulebook() {
        return rulebook;
    }
}
