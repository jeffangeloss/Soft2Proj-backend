package com.app.soft2projbackend.model;

public class Connection {
    private Node fromNode;
    private Node toNode;
    private Boolean condition; // "true", "false", null
    private String fromNodeId;
    private String toNodeId;

    public Connection() { }

    public boolean isCondition(Boolean result) {
        if (condition == null) return true;
        if (result == null) return true;
        return condition;
    }

    public void resolve(Flow flow) {
        this.fromNode = flow.getNodeById(fromNodeId);
        this.toNode = flow.getNodeById(toNodeId);
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }

    public void setCondition(Boolean condition) {
        this.condition = condition;
    }
}
