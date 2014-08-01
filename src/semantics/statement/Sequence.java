package semantics.statement;

import semantics.expression.Expression;

import java.util.HashMap;
import java.util.Map;

public class Sequence implements Statement {

    private Statement first;
    private Statement second;

    public Sequence(Statement first, Statement second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment) {

        Map<Statement, Map<String, Expression>> result = new HashMap<>();

        if (first instanceof DoNothing) {
            result.put(second, environment);
            return result;
        } else {
            Map<Statement, Map<String, Expression>> reducedFirstResult = first.reduce(environment);
            assert reducedFirstResult.size() == 1;
            for (Map.Entry<Statement, Map<String, Expression>> entry : reducedFirstResult.entrySet()) {
                result.put(new Sequence(entry.getKey(), second), environment);
            }
            return result;
        }
    }

    @Override
    public String toString() {
        return first + "; " + second;
    }

    @Override
    public Map<String, Expression> evaluate(Map<String, Expression> environment) {
        // Wow!  Look how simple this is compared to reduce!
        return second.evaluate(first.evaluate(environment));
    }
}
