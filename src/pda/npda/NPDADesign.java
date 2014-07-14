package pda.npda;

import fa.State;
import pda.PDAConfiguration;
import pda.Stack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

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

    public boolean accepts(String string) {
        NPDA npda = toNPDA();
        npda.readString(string);
        return npda.accepting();
    }

    public NPDA toNPDA() {
        Stack startStack = new Stack(Arrays.asList(bottomCharacter));
        PDAConfiguration startConfiguration = new PDAConfiguration(startState, startStack);
        return new NPDA(new HashSet<>(Arrays.asList(startConfiguration)), acceptStates, rulebook);
    }
}
