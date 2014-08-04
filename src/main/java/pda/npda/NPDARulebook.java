package pda.npda;

import pda.PDAConfiguration;
import pda.PDARule;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Essentially, a collection of rules.
 * (1) A collection of rules
 * (2) Logic for how to lookup the appropriate rule for a given configuration/input
 * (3) A call to "follow" on the looked up rule to get next configuration
 */
public class NPDARulebook {
    private List<PDARule> rules;

    public NPDARulebook(List<PDARule> rules) {
        this.rules = rules;
    }

    /**
     * Looks up the appropriate rules based on PDA configuration/character and invokes "follow()" on those rules to get
     * us the possible next configurations.
     * @param configurations the configurations to provide the current possible state/stack combinations to search the
     *                       rules for.
     * @param character the character to search the rules for.
     * @return the new PDA configurations reflecting the possible state/stack changes.
     */
    public Set<PDAConfiguration> nextConfigurations(Set<PDAConfiguration> configurations, Character character) {
        Set<PDAConfiguration> results = new HashSet<>();
        for (PDAConfiguration configuration : configurations) {
            results.addAll(followRulesFor(configuration, character));
        }
        return results;
    }

    /**
     * Loop through all of the rules found to apply to the provided configuration (state + stack) and call follow() on
     * each one to get the next possible configuration.  Capture each configurations and return them all as
     * possibilities.
     * @param configuration the configuration to search the rules for.
     * @param character the character to search the rules for.
     * @return the possible configurations if any/all of the applicable rules are followed.
     */
    public Set<PDAConfiguration> followRulesFor(PDAConfiguration configuration, Character character) {
        Set<PDAConfiguration> results = new HashSet<>();
        for (PDARule rule : rulesFor(configuration, character)) {
            results.add(rule.follow(configuration));
        }
        return results;
    }

    /**
     * Loops through its rules and return any it finds that apply to the given configuration/character.
     * @param configuration the configuration to provide the current state/stack to search the rules for.
     * @param character the character to search the rules for.
     * @return the applicable rules.
     */
    public List<PDARule> rulesFor(PDAConfiguration configuration, Character character) {
        List<PDARule> results = new ArrayList<>();
        for (PDARule rule : rules) {
            if (rule.appliesTo(configuration, character)) {
                results.add(rule);
            }
        }
        return results;
    }

    /**
     * For the provided configurations, scans the rules for any free moves (i.e. any rule where the input character has
     * been specified as null) and then modifies the provided configurations by recursively calling those rules until no
     * more free moves have been discovered.
     * @param configurations the configurations to use as a starting point for discovering/following free moves.
     * @return the modified set of configurations after following any/all free moves available to any of the individual
     *         configurations.
     */
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
