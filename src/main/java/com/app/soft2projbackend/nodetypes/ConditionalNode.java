package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.*;
public class ConditionalNode extends Node {
    private boolean condition;

    public ConditionalNode() {
        this.type = TipoNodo.CONDITIONAL;
        this.politica = PoliticaError.STOP_ON_FAIL;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public boolean isCondition() {
        return condition;
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("condition" + id, condition); // put condition op gon EC
    }
}