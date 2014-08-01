package semantics.smallstep;

import semantics.expression.Expression;
import semantics.statement.Statement;

import java.util.Map;

public class Machine {
    private Statement statement;
    private Map<String, Expression> environment;

    public Machine(Statement statement, Map<String, Expression> environment) {
        this.statement = statement;
        this.environment = environment;
    }

    public void run() {
        while (statement.reducible()) {
            //System.out.println(statement.toString() + ", " + environment.toString());
            step();
        }
        //System.out.println(statement.toString() + ", " + environment.toString());
    }

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
