package semantics.statement;

import semantics.expression.Expression;

import java.util.Map;

/**
 * A contract for Statements (e.g. Assignment, If, While, etc) to adhere to.
 */
public interface Statement {

    /**
     * For use by the Small-Step mechanism (i.e. Machine).  Indicates whether this particular Statement subtype is
     * reducible to a more elemental Statement subtype down the tree (all the way down to a "DoNothing" Statement type,
     * which is not further reducible).
     * @return <code>true</code> if this Statement subtype can be further reduced, <code>false</code> if otherwise.
     */
    public boolean reducible();

    /**
     * For use by the Small-Step mechanism (i.e. Machine).  Incrementally reduces this Statement subtype to a more
     * elemental Statement subtype down the tree (all the way down to a "DoNothing" Statement type, which is not further
     * reducible).
     * Example:  "y = 3" (an Assignment expression) can be reduced by place/update a variable "y" in the environment
     * with the statement itself becoming a "DoNothing".
     * @param environment The environment to be made available to this reduce operation for variable update purposes.
     * @return The Statement incrementally reduced by one step (i.e. "Small-Step")
     */
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment);

    /**
     * For use by the Big-Step mechanism.  Implementations should provide instructions for how to recursively implement
     * themselves down to the most elemental Statement subtype down the tree ("DoNothing").
     * @param environment The environment to be made available to this reduce operation for variable update purposes.
     * @return The resulting Statement after being recursively evaluated down to its most elemental type
     * (i.e. "Big Step")
     */
    public Map<String, Expression> evaluate(Map<String, Expression> environment);
}
