package semantics.statement;

import semantics.expression.Expression;

import java.util.HashMap;
import java.util.Map;

public class Assign implements Statement {
    private String name;
    private Expression expression;

    public Assign(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment) {
        Map<Statement, Map<String, Expression>> result = new HashMap<>();
        if (expression.reducible()) {

            result.put(new Assign(name, expression.reduce(environment)), environment);
            return result;
        } else {
            environment.put(name, expression);
            result.put(new DoNothing(), environment);
            return result;
        }
    }

    @Override
    public String toString() {
        return name + " = " + expression;
    }

    @Override
    public Map<String, Expression> evaluate(Map<String, Expression> environment) {
        environment.put(name, expression.evaluate(environment));
        return environment;
    }
}
