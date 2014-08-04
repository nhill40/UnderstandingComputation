package dtm;


import fa.SingleState;
import fa.State;

import java.util.Arrays;
import java.util.List;

/**
 * This class aids in showing how a DTM can be used to simulate subroutines.  What we end up doing is chaining  several
 * "add 1" Turing Machines together, with each intermediate state being identified by a distinct state
 * (e.g. 0 > 1 > 2 > 3).  We are using the "-1" state as a "finishing" state (the state the larger machine is
 * temporarily in as it returns the tape head to the starting position).
 */
public class IncrementRules {

    public static List<TMRule> getIncrementRules(State startState, State returnState) {
        State incrementing = startState;
        State finishing = new SingleState(-1);
        State finished = returnState;

        return Arrays.asList(
                new TMRule(incrementing, '0', finishing, '1', Direction.RIGHT),
                new TMRule(incrementing, '1', incrementing, '0', Direction.LEFT),
                new TMRule(incrementing, '_', finishing, '1', Direction.RIGHT),
                new TMRule(finishing, '0', finishing, '0', Direction.RIGHT),
                new TMRule(finishing, '1', finishing, '1', Direction.RIGHT),
                new TMRule(finishing, '_', finished, '_', Direction.LEFT)
        );
    }
}
