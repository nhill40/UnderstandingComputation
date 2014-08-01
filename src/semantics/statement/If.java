package semantics.statement;

import semantics.expression.Boolean;
import semantics.expression.Expression;

import java.util.HashMap;
import java.util.Map;

public class If implements Statement {

    private Expression condition;
    private Statement consequence;
    private Statement alternative;

    public If(Expression condition, Statement consequence, Statement alternative) {
        this.condition = condition;
        this.consequence = consequence;
        this.alternative = alternative;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment) {
        Map<Statement, Map<String, Expression>> result = new HashMap<>();
        if (condition.reducible()) {
            result.put(new If(condition.reduce(environment), consequence, alternative), environment);
            return result;
        } else {
            assert(condition instanceof semantics.expression.Boolean);
            switch (((Boolean) condition).getValue()) {
                case 1:
                    result.put(consequence, environment);
                    return result;
                case 0:
                    result.put(alternative, environment);
                    return result;
                default:
                    throw new RuntimeException("Undefined");
            }
        }
    }

    @Override
    public String toString() {
        return "if (" + condition + ") { " + consequence + " } else { " + alternative + " }";
    }

    @Override
    public Map<String, Expression> evaluate(Map<String, Expression> environment) {
        Boolean evaluatedCondition = (Boolean) condition.evaluate(environment);
        switch (evaluatedCondition.getValue()) {
            case 1:
                return consequence.evaluate(environment);
            case 0:
                return alternative.evaluate(environment);
            default:
                throw new RuntimeException("Undefined");
        }
    }
}
