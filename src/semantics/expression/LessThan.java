package semantics.expression;

import java.util.Map;

/**
 * A LessThan expression where the left and right expressions must reduce/evaluate down to Numbers.  Examples:
 *     1 < 2
 *     x < y
 *    (2 * y) < x
 */
public class LessThan implements Expression {

    private Expression left;
    private Expression right;

    public LessThan(Expression left, Expression right) {
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
            return new LessThan(left.reduce(environment), right);
        } else if (right.reducible()) {
            return new LessThan(left, right.reduce(environment));
        } else {
            assert(left instanceof Number);
            assert(right instanceof Number);
            if (((Number) left).getValue() < ((Number) right).getValue()) {
                return new semantics.expression.Boolean(1);
            } else {
                return new Boolean(0);
            }
        }
    }

    @Override
    public String toString() {
        return left + " < " + right;
    }

    @Override
    public Expression evaluate(Map<String, Expression> environment) {
        Number evaluatedLeft = ((Number) left.evaluate(environment));
        Number evaluatedRight = ((Number) right.evaluate(environment));
        if (evaluatedLeft.getValue() < evaluatedRight.getValue()) {
            return new Boolean(1);
        } else {
            return new Boolean(0);
        }
    }
}
