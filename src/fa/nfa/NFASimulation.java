package fa.nfa;

import fa.FARule;
import fa.State;

import java.util.HashSet;
import java.util.Set;

public class NFASimulation {
    private NFADesign nfaDesign;

    public NFASimulation(NFADesign nfaDesign) {
        this.nfaDesign = nfaDesign;
    }

    public Set<State> nextStates(Set<State> states, Character character) {
        NFA nfa = nfaDesign.toNFA(states);
        nfa.readCharacter(character);
        return nfa.getCurrentStatesConsideringFreeMoves();
    }

    public Set<State> rulesFor(Set<State> states) {
        Set<State> results = new HashSet<State>();

        // TODO: not sure what this represents yet, so just using a placeholder:
        State state = new State();

        for (Character character : nfaDesign.getRulebook().alphabet()) {
            //results.add(new FARule(state, character, nextStates(state, character)))
        }
        return null;
    }
}
