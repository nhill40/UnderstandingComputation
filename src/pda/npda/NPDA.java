package pda.npda;

import fa.State;
import pda.PDAConfiguration;

import java.util.List;
import java.util.Set;

public class NPDA {
    private Set<PDAConfiguration> currentConfigurations;
    private List<State> acceptStates;
    private NPDARulebook rulebook;

    public NPDA(Set<PDAConfiguration> currentConfigurations, List<State> acceptStates, NPDARulebook rulebook) {
        this.currentConfigurations = currentConfigurations;
        this.acceptStates = acceptStates;
        this.rulebook = rulebook;
    }

    public boolean accepting() {
        for (PDAConfiguration currentConfiguration : getCurrentConfigurations()) {
            if (acceptStates.contains(currentConfiguration.getState())) {
                return true;
            }
        }
        return false;
    }

    public void readCharacter(Character character) {
        currentConfigurations = rulebook.nextConfigurations(getCurrentConfigurations(), character);
    }

    public void readString(String string) {
        for (Character character : string.toCharArray()) {
            readCharacter(character);
        }
    }

    public Set<PDAConfiguration> getCurrentConfigurations() {
        return rulebook.followFreeMoves(currentConfigurations);
    }
}
