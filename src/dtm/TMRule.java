package dtm;

import fa.State;

/**
 * A TM rule is a combination of current state, a tape character (for lookup purposes), nextState (what state to move
 * to next), as well as a "write character" (what character to write to the tape in place of the character found) and
 * a direction to indicate which way the tape head should move next upon writing its character.
 */
public class TMRule {
    private State state;
    private Character character;
    private State nextState;
    private Character writeCharacter;
    private Direction direction;

    public TMRule(State state, Character character, State nextState, Character writeCharacter, Direction direction) {
        this.state = state;
        this.character = character;
        this.nextState = nextState;
        this.writeCharacter = writeCharacter;
        this.direction = direction;
    }

    /**
     * Determines if this particular rule applies to the given TM configuration.
     * @param configuration the TM configuration to use to pull the current state and current tape character from.
     * @return whether this rule applies to the given TM configuration.
     */
    public boolean appliesTo(TMConfiguration configuration) {
        return state.equals(configuration.getState()) && character.equals(configuration.getTape().getMiddle());
    }

    /**
     * Return a newly constructed TM Configuration built using this rule's specified nextState as well as a newly
     * constructed Tape based upon the configuration changes (the write character + indicated direction) specified by
     * this rule.
     * @param configuration the TM Configuration to use as a starting point for modifying the tape.
     * @return the newly constructed TM Configuration, containing this rule's nextState as its current state and a
     * modified tape upon this rule's write character and direction.
     */
    public TMConfiguration follow(TMConfiguration configuration) {
        return new TMConfiguration(nextState, nextTape(configuration));
    }

    /**
     * A method that modifies a given TM Configuration's tape by overwriting it's current (i.e. "middle") character and
     * then moving the tape head in the appropriate direction.
     * @param configuration the configuration to pull the starting point tape from.
     * @return the modified tape.
     */
    public Tape nextTape(TMConfiguration configuration) {
        Tape writtenTape = configuration.getTape().write(writeCharacter);

        switch (direction) {
            case LEFT:
                return writtenTape.moveHeadLeft();
            case RIGHT:
                return writtenTape.moveHeadRight();
            default:
                throw new RuntimeException();
        }
    }
}
