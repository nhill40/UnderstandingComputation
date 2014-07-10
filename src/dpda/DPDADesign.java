package dpda;

import fa.State;

import java.util.Arrays;
import java.util.List;

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

    public boolean accepts(String string) {
        DPDA dpda = toDPDA();
        dpda.readString(string);
        return dpda.accepting();
    }

    public DPDA toDPDA() {
        Stack startStack = new Stack(Arrays.asList('$'));
        PDAConfiguration startConfiguration = new PDAConfiguration(startState, startStack);
        return new DPDA(startConfiguration, acceptStates, rulebook);
    }
}
