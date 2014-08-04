package semantics.statement;

import semantics.expression.Expression;

import java.util.Map;

/**
 * A DoNothing Statement essentially serves as a marker to indicate that the process of reducing/evaluation this
 * Statement has reached its logical conclusion (i.e. cannot be any further reduced/evaluated).
 */
public class DoNothing implements Statement {

    @Override
    public boolean reducible() {
        return false;
    }

    @Override
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment) {
        return null; // NO OP
    }

    @Override
    public String toString() {
        return "do-nothing";
    }

    @Override
    public Map<String, Expression> evaluate(Map<String, Expression> environment) {
        // Literally do nothing!
        return environment;
    }
}
