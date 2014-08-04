package semantics.smallstep;

import semantics.expression.Expression;
import semantics.statement.Statement;

import java.util.Map;

/**
 * To allow us to perform Small-Step reductions of Statements, we need this simple Machine that allows us to repeatedly
 * check to see if a statement is reducible and further reduce said statement if so (a reduction "step").  This machine
 * is also responsible for capturing the results of each step (the reduced statement + modified environment), thereby
 * making it stateful.
 * This is probably the best illustration of the difference between Small-Step and Big-Step semantics in that no
 * corresponding "Machine" concept exists for Big-Step - which instead relies on recursion to repeatedly drill down
 * through the syntax tree until it hits bottom (a "DoNothing" statement).
 */
public class Machine {
    private Statement statement;
    private Map<String, Expression> environment;

    public Machine(Statement statement, Map<String, Expression> environment) {
        this.statement = statement;
        this.environment = environment;
    }

    /**
     * Repeatedly calls step to incrementally reduce the statement until the statement reports back that it is no further
     * reducible.
     */
    public void run() {
        while (statement.reducible()) {
            //System.out.println(statement.toString() + ", " + environment.toString());
            step();
        }
        //System.out.println(statement.toString() + ", " + environment.toString());
    }

    /**
     * Perform an incremental reduction on the statement, capturing the result as the next statement/environment.
     */
    public void step() {
        Map<Statement, Map<String, Expression>> result = statement.reduce(environment);
        assert(result.size() < 2);
        for (Map.Entry<Statement, Map<String, Expression>> entry : result.entrySet()) {
            statement = entry.getKey();
            environment = entry.getValue();
        }
    }

    @Override
    public String toString() {
        return statement.toString() + ", " + environment.toString();
    }
}
