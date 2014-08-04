package semantics.expression;

import java.util.Map;

/**
 * A contract for Expressions (e.g. Add, Multiply, Variable, Number) to adhere to.
 */
public interface Expression {

    /**
     * For use by the Small-Step mechanism (i.e. Machine).  Indicates whether this particular Expression subtype is
     * reducible to a more elemental Expression subtype down the tree (which, for the purposes of this simulation,
     * always ends with Boolean or Number).
     * @return <code>true</code> if this Expression subtype can be further reduced, <code>false</code> if otherwise.
     */
    public boolean reducible();

    /**
     * For use by the Small-Step mechanism (i.e. Machine).  Incrementally reduces this Expression subtype to a more
     * elemental Expression subtype down the tree (which, for the purposes of this simulation, always ends with Boolean
     * or Number).
     * Example:  "3 + 4" (an Add expression) can be reduced to "7" (a Number expression).
     * @param environment The environment to be made available to this reduce operation for variable lookup but not
     *                    update purposes (Expressions themselves should *not* alter the environment).
     * @return The Expression incrementally reduced by one step (i.e. "Small-Step")
     */
    public Expression reduce(Map<String, Expression> environment);

    /**
     * For use by the Big-Step mechanism.  Implementations should provide instructions for how to recursively implement
     * themselves down to the most elemental Expression subtype down the tree (which, for the purposes of this
     * simulation, always ends with Boolean or Number).
     * @param environment The environment to be made available to this evaluate operation for variable lookup but not
     *                    update purposes (Expressions themselves should *not* alter the environment).
     * @return The resulting Expression after being recursively evaluated down to its most elemental type
     * (i.e. "Big Step")
     */
    public Expression evaluate(Map<String, Expression> environment);
}
