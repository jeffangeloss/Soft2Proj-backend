package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.ExecutionContext;
import com.app.soft2projbackend.Nodo;
import com.app.soft2projbackend.enums.*;

public class ConditionalNode extends Nodo {
    private String conditionKey;

    public ConditionalNode(String name, String conditionKey) {
        super(name, TipoNodo.CONDITIONAL);
        this.conditionKey = conditionKey;
    }

    @Override
    public void execute(ExecutionContext context) {
        Object value = context.get(conditionKey);
        boolean result = value != null;

        context.put("conditionResult", result);
    }
}
