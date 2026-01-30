package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Engine {

    public ExecutionContext run(Flow flow) {

        ExecutionContext context = new ExecutionContext();
        Nodo current = flow.getStartNode();

        while (current != null) {
            try {
                current.execute(context);
                current = getNextNode(flow, current, context);
            } catch (Exception e) {
                if (current.getErrorPolicy() == PoliticaError.STOP_ON_FAIL) {
                    throw new RuntimeException(e);
                }
                current = getNextNode(flow, current, context);
            }
        }
        return context;
    }

    private Nodo getNextNode(Flow flow, Nodo current, ExecutionContext context) {

        List<Connection> outs = flow.getConnectionsFrom(current.getId());

        if (current.getType() == TipoNodo.CONDITIONAL) {
            Boolean result = (Boolean) context.get("conditionResult");
            return outs.stream()
                    .filter(c -> c.matches(result))
                    .findFirst()
                    .map(c -> flow.getNodeById(c.getToNodeId()))
                    .orElse(null);
        }

        return outs.isEmpty() ? null : flow.getNodeById(outs.get(0).getToNodeId());
    }
}