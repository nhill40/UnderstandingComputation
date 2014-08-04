package semantics.expression;

import java.util.Map;

/**
 * A Variable expression that can be further reduced/evaluated by looking up its value (which, for the purposes of this
 * simulation, must be either a Number or Boolean) within the Environment.
 */
public class Variable implements Expression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Expression reduce(Map<String, Expression> environment) {
        return environment.get(name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Expression evaluate(Map<String, Expression> environment) {
        return environment.get(name);
    }
}
