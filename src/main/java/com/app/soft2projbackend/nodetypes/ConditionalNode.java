package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Nodo;
import com.app.soft2projbackend.enums.*;

public class ConditionalNode extends Nodo {
    private String contextKey;
    private String operator;
    private Object expectedValue;

    public ConditionalNode(String name, String contextKey, String operator, Object expectedValue) {
        super(name, TipoNodo.CONDITIONAL);
        this.contextKey = contextKey;
        this.operator = operator;
        this.expectedValue = expectedValue;
    }

    @Override
    public void execute(ExecutionContext context) {
        Object actual = context.get(contextKey);
        boolean result = actual != null && actual.equals(expectedValue);

        context.put("conditionResult", result);
    }
}
