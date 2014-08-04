package semantics.expression;

import java.util.Map;

/**
 * A Boolean expression - can be only true (1) or false (0).
 */
public class Boolean implements Expression {

    // TODO: re-evaluate to see if this can just be a java Boolean to simplify implementation a bit.
    private int value;

    public Boolean(int value) {
        this.value = value;
    }

    @Override
    public boolean reducible() {
        return false;
    }

    @Override
    public Expression reduce(Map<String, Expression> environment) {
        return null;  // NO OP
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        if (value == 0) {
            return "false";
        } else if (value == 1) {
            return "true";
        } else {
            throw new RuntimeException("Undefined");
        }
    }

    @Override
    public Expression evaluate(Map<String, Expression> environment) {
        return this;
    }
}
