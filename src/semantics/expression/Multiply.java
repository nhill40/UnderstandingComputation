package semantics.expression;

import java.util.Map;

/**
 * An Add expression where the left and right expressions must evaluate/reduce down to Numbers.  Examples:
 *       3 * 4
 *       x * y
 *      (y + 2) * 5
 */
public class Multiply implements Expression {

    private Expression left;
    private Expression right;

    public Multiply(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean reducible() {
        return true;
    }

    @Override
    public Expression reduce(Map<String, Expression> environment) {
        if (left.reducible()) {
            return new Multiply(left.reduce(environment), right);
        } else if (right.reducible()) {
            return new Multiply(left, right.reduce(environment));
        } else {
            return new Number(((Number) left).getValue() * ((Number) right).getValue());
        }
    }

    @Override
    public String toString() {
        return left + " * " + right;
    }

    @Override
    public Expression evaluate(Map<String, Expression> environment) {
        Number evaluatedLeft = (Number) left.evaluate(environment);
        Number evaluatedRight = (Number) right.evaluate(environment);
        return new Number(evaluatedLeft.getValue() * evaluatedRight.getValue());
    }
}
