package com.app.soft2projbackend.model;

import java.util.*;

public class Flow {
    private String id;
    private String name;
    private List<Node> nodes = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public Flow() {} //constructor vacio obligatorio, no cambiar, será usado por Springboot

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

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    } //setter debe coincidir con JSON

    public void setConnections(List<Connection> connections) {
        this.connections = connections;
    } //setter debe coincidir con JSON

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public List<Node> getNodos() {
        return nodes;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Node getStartNode() {
        return nodes.stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .findFirst()
                .orElseThrow();
    }

    public Node getNodeById(String id) {
        return nodes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Nodo no encontrado: " + id)
                );
    }
}
