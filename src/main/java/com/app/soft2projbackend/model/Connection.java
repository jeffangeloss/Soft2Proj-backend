package com.app.soft2projbackend.model;

public class Connection {
    private String fromNodeId;
    private String toNodeId;
    private String condition; // "true", "false", null

    public Connection(String from, String to, String condition) {
        this.fromNodeId = from;
        this.toNodeId = to;
        this.condition = condition;
    }

    public boolean matches(Boolean result) {
        if (condition == null) return true;
        return condition.equalsIgnoreCase(result.toString());
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }
}
