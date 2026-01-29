package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.model.Connection;
import com.app.soft2projbackend.model.Nodo;
import com.app.soft2projbackend.model.TipoNodo;

import java.util.*;

public class WorkflowMap {
    private String id;
    private String name;
    private List<Nodo> nodes = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public WorkflowMap() {} //constructor vacio obligatorio, no cambiar, será usado por Springboot

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

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodes(List<Nodo> nodes) {
        this.nodes = nodes;
    }

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Nodo getStartNode() {
        return nodes.stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .findFirst()
                .orElseThrow();
    }
}
