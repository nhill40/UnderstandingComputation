package semantics.bigstep;

import org.junit.Test;
import semantics.SemanticsTest;
import semantics.expression.Add;
import semantics.expression.Expression;
import semantics.expression.LessThan;
import semantics.expression.Multiply;
import semantics.expression.Number;
import semantics.expression.Variable;
import semantics.statement.Assign;
import semantics.statement.If;
import semantics.statement.Sequence;
import semantics.statement.Statement;
import semantics.statement.While;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class EvaluateTest implements SemanticsTest {

    @Test
    @Override
    public void test_addTwoVarsAndAssign() {
        Expression add = new Add(new Variable("x"), new Variable("y"));
        Statement assign = new Assign("x", add);

        Map<String, Expression> environment = new HashMap<>();
        environment.put("x", new semantics.expression.Number(3));
        environment.put("y", new Number(4));

        assign.evaluate(environment);
        assertEquals("{y=4, x=7}", environment.toString());
    }

    @Test
    @Override
    public void test_addVarToNumberAndAssign() {
        Expression add = new Add(new Variable("x"), new Number(1));
        Statement assign = new Assign("x", add);

        Map<String, Expression> environment = new HashMap<>();
        environment.put("x", new Number(2));

        assign.evaluate(environment);
        assertEquals("{x=3}", environment.toString());
    }

    @Test
    @Override
    public void test_if() {
        Statement consequence = new Assign("y", new Number(1));
        Statement alternative = new Assign("y", new Number(2));
        If ifStatement = new If(new Variable("x"), consequence, alternative);

        Map<String, Expression> environment = new HashMap<>();
        environment.put("x", new semantics.expression.Boolean(1));

        ifStatement.evaluate(environment);
        assertEquals("{y=1, x=true}", environment.toString());
    }

    @Test
    @Override
    public void test_sequence() {
        Expression firstAdd = new Add(new Number(1), new Number(1));
        Statement first = new Assign("x", firstAdd);

        Expression secondAdd = new Add(new Variable("x"), new Number(3));
        Statement second = new Assign("y", secondAdd);

        Sequence sequence = new Sequence(first, second);

        Map<String, Expression> environment = new HashMap<>();
        sequence.evaluate(environment);
        assertEquals("{y=5, x=2}", environment.toString());
    }

    @Test
    @Override
    public void test_while() {
        Expression lessThan = new LessThan(new Variable("x"), new Number(5));
        Statement assign = new Assign("x", new Multiply(new Variable("x"), new Number(3)));
        While whileStatement = new While(lessThan, assign);

        Map<String, Expression> environment = new HashMap<>();
        environment.put("x", new Number(1));

        whileStatement.evaluate(environment);
        assertEquals("{x=9}", environment.toString());
    }
}
