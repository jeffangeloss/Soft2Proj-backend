package com.app.soft2projbackend.handlers;

import java.util.List;

import com.app.soft2projbackend.exceptions.MultipleConnectionsException;
import org.springframework.stereotype.Service;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.nodetypes.ConditionalNode;

@Service
public class Engine {

    public ExecutionContext run(Flow flow) {

        ExecutionContext context = new ExecutionContext();
        context.setFlow(flow);
        Node current = flow.getStartNode();

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

    private Node getNextNode(Flow flow, Node current, ExecutionContext context) {

        List<Connection> outs = flow.getConnectionsFrom(current);// List of Node connections
        outs.forEach(o -> {
            System.out.println(o.isCondition());
        });
        System.out.println("------------------------");

        if (current.getType() == TipoNodo.CONDITIONAL) {
            boolean way = ((ConditionalNode) current).isCondition();
            System.out.println(way);
            Connection selConn = outs.stream()
                    .filter(c -> c.isCondition() == way)
                    .findFirst()
                    .orElse(null);
            System.out.println(selConn.isCondition());
            System.out.println(selConn.getToNode());
            return selConn.getToNode();
        }

        return outs.isEmpty() ? null : outs.getFirst().getToNode();
    }
}