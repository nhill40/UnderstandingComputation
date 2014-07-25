package dtm;

import java.util.List;

/**
 * Essentially, a collection of rules.
 * (1) A collection of rules
 * (2) Logic for how to lookup the appropriate rule for a given state/tape (i.e. configuration)
 * (3) A call to "follow" on the looked up rule to get next configuration
 */
public class DTMRulebook {
    private List<TMRule> rules;

    public DTMRulebook(List<TMRule> rules) {
        this.rules = rules;
    }

    /**
     * Looks up the appropriate rule based on TM configuration and invokes "follow()" on that rule to get us
     * the next configuration.
     * @param configuration the configuration to provide the current state/tape to search the rules for.
     * @return the new TM configuration reflecting the state/tape change.
     */
    public TMConfiguration nextConfiguration(TMConfiguration configuration) {
        return ruleFor(configuration).follow(configuration);
    }

    /**
     * Loops through its rules and returns the first one it finds that applies to the given configuration.
     * @param configuration the configuration to provide the current state/tape to search the rules for.
     * @return the applicable rule.
     */
    public TMRule ruleFor(TMConfiguration configuration) {
        for (TMRule rule : rules) {
            if (rule.appliesTo(configuration)) return rule;
        }
        return null;
    }

    /**
     * Checks to see if this rulebook contains any valid rules for the provided configuration (state + tape).
     * @param configuration the configuration to use in checking for a valid rule.
     * @return <code>true</code> if a valid rule was found for the provided configuration, <code>false</code> if
     * otherwise.
     */
    public boolean appliesTo(TMConfiguration configuration) {
        return ruleFor(configuration) != null;
    }
}
