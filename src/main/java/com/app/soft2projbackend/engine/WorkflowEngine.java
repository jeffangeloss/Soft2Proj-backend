package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.enums.*;
import com.app.soft2projbackend.model.Connection;
import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Nodo;

import java.util.List;

public class WorkflowEngine {

    public void run(Workflow workflow) {

        ExecutionContext context = new ExecutionContext();
        Nodo current = workflow.getStartNode();

        while (current != null) {
            try {
                current.execute(context);
                current = getNextNode(workflow, current, context);
            } catch (Exception e) {
                if (current.getErrorPolicy() == PoliticaError.STOP_ON_FAIL) {
                    throw new RuntimeException(e);
                }
                current = getNextNode(workflow, current, context);
            }
        }
    }

    private Nodo getNextNode(Workflow workflow, Nodo current, ExecutionContext context) {

        List<Connection> outs = workflow.getConnectionsFrom(current.getId());

        if (current.getType() == TipoNodo.CONDITIONAL) {
            Boolean result = (Boolean) context.get("conditionResult");
            return outs.stream()
                    .filter(c -> c.matches(result))
                    .findFirst()
                    .map(c -> workflow.getNode(c.getToNodeId()))
                    .orElse(null);
        }

        return outs.isEmpty() ? null : workflow.getNode(outs.get(0).getToNodeId());
    }
}