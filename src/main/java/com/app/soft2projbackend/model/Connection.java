package com.app.soft2projbackend.model;

public class Connection {
    private Node fromNode;
    private Node toNode;
    private Boolean condition; // "true", "false", null

    public Connection() { }

    public boolean isCondition(Boolean result) {
        if (condition == null) return true;
        if (result == null) return true;
        return condition;
    }

    public Node getFromNode() {
        return fromNode;
    }

    public Node getToNode() {
        return toNode;
    }
}
