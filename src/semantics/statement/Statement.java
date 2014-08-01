package semantics.statement;

import semantics.expression.Expression;

import java.util.Map;

public interface Statement {
    public boolean reducible();

    // Small step
    public Map<Statement, Map<String, Expression>> reduce(Map<String, Expression> environment);

    // Big Step
    public Map<String, Expression> evaluate(Map<String, Expression> environment);
}
