package semantics.statement;

import semantics.expression.Boolean;
import semantics.expression.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * A While statement allows us to repeatedly evaluate/reduce a "body" statement until the evaluation/reduction of the
 * "condition" indicates false.  Example:
 *      while (x < 10)
 *          x = x + 1
 */
public class While implements Statement {

    private Expression condition;
    private Statement body;

    public While(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment) {
        Map<Statement, Map<String, Expression>> result = new HashMap<>();
        result.put(new If(condition, new Sequence(body, this), new DoNothing()), environment);
        return result;
    }

    @Override
    public String toString() {
        return "while (" + condition + ") { " + body + " }";
    }

    @Override
    public Map<String, Expression> evaluate(Map<String, Expression> environment) {
        Boolean evaluatedCondition = (Boolean) condition.evaluate(environment);
        switch (evaluatedCondition.getValue()) {
            case 1:
                return evaluate(body.evaluate(environment));
            case 0:
                return environment;
            default:
                throw new RuntimeException("Undefined");
        }

    }
}
