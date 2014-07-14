package pda.dpda;

import pda.PDAConfiguration;
import pda.PDARule;

import java.util.List;

/**
 * Essentially, a collection of rules.
 * (1) A collection of rules
 * (2) Logic for how to lookup the appropriate rule for a given configuration/input
 * (3) A call to "follow" on the looked up rule to get next configuration
 */
public class DPDARulebook {
    private List<PDARule> rules;

    public DPDARulebook(List<PDARule> rules) {
        this.rules = rules;
    }

    /**
     * Looks up the appropriate rule based on PDA configuration/character and invokes "follow()" on that rule to get us
     * the next configuration.
     * @param configuration the configuration to provide the current state/stack to search the rules for.
     * @param character the character to search the rules for.
     * @return the new PDA configuration reflecting the state/stack change.
     */
    public PDAConfiguration nextConfiguration(PDAConfiguration configuration, Character character) {
        return ruleFor(configuration, character).follow(configuration);
    }

    /**
     * Loops through its rules and returns the first one it finds that applies to the given configuration/character.
     * @param configuration the configuration to provide the current state/stack to search the rules for.
     * @param character the character to search the rules for.
     * @return the applicable rule.
     */
    public PDARule ruleFor(PDAConfiguration configuration, Character character) {
        for (PDARule rule : rules) {
            if (rule.appliesTo(configuration, character)) {
                return rule;
            }
        }
        return null;
    }

    /**
     * Checks to see if this rulebook contains any valid rules for the provided configuration (state + stack) and
     * character.
     * @param configuration the configuration to use in checking for a valid rule.
     * @param character the character to use in checking for a valid rule.
     * @return <code>true</code> if a valid rule was found for the provided configuration/character, <code>false</code>
     *         if otherwise.
     */
    public boolean appliesTo(PDAConfiguration configuration, Character character) {
        return (ruleFor(configuration, character) != null);
    }

    /**
     * For the provided configuration, scans the rules for any free moves (i.e. any rule where the input character has
     * been specified as null) and then modifies the provided configuration by recursively calling those rules until no
     * more free moves have been discovered.
     * @param configuration the configuration to use as a starting point for discovering/following free moves.
     * @return the modified configuration after following any/all free moves available to it.
     */
    public PDAConfiguration followFreeMoves(PDAConfiguration configuration) {
        if (appliesTo(configuration, null)) {
            configuration = followFreeMoves(nextConfiguration(configuration, null));
        }
        return configuration;
    }
}
