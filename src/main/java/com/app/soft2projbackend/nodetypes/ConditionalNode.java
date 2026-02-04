package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;

public class ConditionalNode extends Node {
    private boolean condition;

    public ConditionalNode(String id, String name, PoliticaError politica, String message, boolean condition) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.CONDITIONAL;
        this.politica = politica;
        this.message = message;
        this.condition = condition;
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("conditionResult", condition);
    }

    public boolean getCondition() {
        return this.condition;
    }
}
