package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;

public class ConditionalNode extends Node {
    private boolean condition;

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public ConditionalNode() {
        this.type = TipoNodo.CONDITIONAL;
        this.politica = PoliticaError.STOP_ON_FAIL;
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("conditionResult", condition); // put mou os code op
    }

    public boolean getCondition() {
        return this.condition;
    }
}
