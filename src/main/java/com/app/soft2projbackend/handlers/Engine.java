package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.steprun.StepRun;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Engine {

    public ExecutionContext run(Flow flow) {

        ExecutionContext context = new ExecutionContext();
        Node current = flow.getStartNode();
        StepRun stepRun = new StepRun(current.getId());

        while (current != null) {
            try {
                current.execute(context, stepRun);
                current = getNextNode(flow, current, context);
            } catch (Exception e) {
                if (current.getErrorPolicy() == PoliticaError.STOP) {
                    throw new RuntimeException(e);
                }
                current = getNextNode(flow, current, context);
            }
        }
        return context;
    }

    private Node getNextNode(Flow flow, Node current, ExecutionContext context) {

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