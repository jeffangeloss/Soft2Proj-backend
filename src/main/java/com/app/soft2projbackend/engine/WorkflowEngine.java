package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.enums.*;
import com.app.soft2projbackend.model.Connection;
import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Nodo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkflowEngine {

    public ExecutionContext run(WorkflowMap workflowMap) {

        ExecutionContext context = new ExecutionContext();
        Nodo current = workflowMap.getStartNode();

        while (current != null) {
            try {
                current.execute(context);
                current = getNextNode(workflowMap, current, context);
            } catch (Exception e) {
                if (current.getErrorPolicy() == PoliticaError.STOP_ON_FAIL) {
                    throw new RuntimeException(e);
                }
                current = getNextNode(workflowMap, current, context);
            }
        }
        return context;
    }

    private Nodo getNextNode(WorkflowMap workflowMap, Nodo current, ExecutionContext context) {

        List<Connection> outs = workflowMap.getConnectionsFrom(current.getId());

        if (current.getType() == TipoNodo.CONDITIONAL) {
            Boolean result = (Boolean) context.get("conditionResult");
            return outs.stream()
                    .filter(c -> c.matches(result))
                    .findFirst()
                    .map(c -> workflowMap.getNode(c.getToNodeId()))
                    .orElse(null);
        }

        return outs.isEmpty() ? null : workflowMap.getNode(outs.get(0).getToNodeId());
    }
}