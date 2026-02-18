package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.MultipleConnectionsException;
import com.app.soft2projbackend.model.*;

import java.util.List;

public class ConditionalNode extends Node {
    private boolean condition;
    private String target;

    public ConditionalNode() {
        this.type = TipoNodo.CONDITIONAL;
        this.politica = PoliticaError.STOP_ON_FAIL;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }
    public void setTarget(String target) {
        this.target = target;
    }
    public boolean isCondition() {
        return this.condition;
    }
    public String getTarget() {
        return this.target;
    }

    @Override
    public void execute(ExecutionContext context) {
        Node toLook = context.getFlow().getNodeById(target);
        Variable val = context.getVariableList()
                .stream()
                .filter(v -> v.getKey().equalsIgnoreCase("conditionResult"+ toLook.getId()))
                .findFirst()
                .orElse(null);
        assert val != null;
        boolean way = (boolean) val.getValue();

        this.condition = way;
        context.put("conditionResult", way);
    }

    private Node getPrevNode(Flow flow, Node current, ExecutionContext context) {
        List<Connection> outs = flow.getConnectionsTo(current);
        if (outs.size() > 1) throw new MultipleConnectionsException();
        return outs.getFirst().getFromNode();// List of Node connections
    }
}