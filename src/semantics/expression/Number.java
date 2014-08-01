package semantics.expression;

import java.util.Map;

/**
 * A Number expression representing a value which cannot be any further reduced/evaluated on its own.
 */
public class Number implements Expression {
    private int value;

    public Number(int value) {
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
        return Integer.toString(value);
    }

    @Override
    public Expression evaluate(Map<String, Expression> environment) {
        return this;
    }
}
