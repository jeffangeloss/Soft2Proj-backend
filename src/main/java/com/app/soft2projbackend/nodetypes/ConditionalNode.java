package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.steprun.StepRun;

public class ConditionalNode extends Node {
    private boolean condition;

    public ConditionalNode(String id, String name, PoliticaError politica, String message) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.CONDITIONAL;
        this.politica = politica;
        this.message = message;
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("conditionResult", condition);
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }
}
