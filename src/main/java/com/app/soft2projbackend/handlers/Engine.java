package com.app.soft2projbackend.handlers;

import java.util.List;
import org.springframework.stereotype.Service;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.nodetypes.ConditionalNode;

@Service
public class Engine {

    public ExecutionContext run(Flow flow) {

        ExecutionContext context = new ExecutionContext();
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

        List<Connection> outs = flow.getConnectionsFrom(current); // List of Node connections

        if (current.getType() == TipoNodo.CONDITIONAL) {
            Boolean way = ((ConditionalNode) current).isCondition();
            Connection selConn =  outs.stream()
                    .filter(c -> c.isCondition(way))
                    .findFirst()
                    .orElse(null);
            System.out.println(selConn);
            assert selConn != null;
            return selConn.getToNode();
        }

        return outs.isEmpty() ? null : outs.getFirst().getToNode();
    }
}