package pda.npda;

import pda.PDAConfiguration;
import pda.PDARule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NPDARulebook {
    private List<PDARule> rules;

    public NPDARulebook(List<PDARule> rules) {
        this.rules = rules;
    }

    public Set<PDAConfiguration> nextConfigurations(Set<PDAConfiguration> configurations, Character character) {
        Set<PDAConfiguration> results = new HashSet<>();
        for (PDAConfiguration configuration : configurations) {
            results.addAll(followRulesFor(configuration, character));
        }
        return results;
    }

    public Set<PDAConfiguration> followRulesFor(PDAConfiguration configuration, Character character) {
        Set<PDAConfiguration> results = new HashSet<>();
        for (PDARule rule : rulesFor(configuration, character)) {
            results.add(rule.follow(configuration));
        }
        return results;
    }

    public List<PDARule> rulesFor(PDAConfiguration configuration, Character character) {
        List<PDARule> results = new ArrayList<>();
        for (PDARule rule : rules) {
            if (rule.appliesTo(configuration, character)) {
                results.add(rule);
            }
        }
        return results;
    }

    public Set<PDAConfiguration> followFreeMoves(Set<PDAConfiguration> configurations) {
        Set<PDAConfiguration> moreConfigurations = nextConfigurations(configurations, null);

        if (configurations.containsAll(moreConfigurations)) {
            return configurations;
        }

        Set<PDAConfiguration> combinedConfigurations = new HashSet<>();
        combinedConfigurations.addAll(moreConfigurations);
        combinedConfigurations.addAll(configurations);
        configurations = followFreeMoves(combinedConfigurations);
        return configurations;
    }
}
