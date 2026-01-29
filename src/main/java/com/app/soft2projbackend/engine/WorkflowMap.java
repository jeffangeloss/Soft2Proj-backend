package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.model.Connection;
import com.app.soft2projbackend.model.Nodo;
import com.app.soft2projbackend.model.TipoNodo;

import java.util.*;

public class WorkflowMap {
    private String id;
    private String name;
    private Map<String, Nodo> nodes = new HashMap<>();
    private List<Connection> connections = new ArrayList<>();

    public WorkflowMap(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public void addNode(Nodo node) {
        nodes.put(node.getId(), node);
    }

    public void addConnection(Connection connection) {
        connections.add(connection);
    }

    public Nodo getNode(String id) {
        return nodes.get(id);
    }

    public Nodo getStartNode() {
        return nodes.values().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .findFirst()
                .orElseThrow();
    }

    public Map<String, Nodo> getNodos() {
        return nodes;
    }

    public List<Connection> getConnectionsFrom(String nodeId) {
        return connections.stream()
                .filter(c -> c.getFromNodeId().equals(nodeId))
                .toList();
    }
}
