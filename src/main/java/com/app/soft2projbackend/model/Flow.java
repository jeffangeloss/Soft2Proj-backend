package com.app.soft2projbackend.model;

import com.app.soft2projbackend.nodetypes.ConditionalNode;

import java.util.*;

public class Flow {
    private String id;
    private String name;
    private List<Node> nodes = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public Flow() {}

    /**
     * Filtra y retorna todas las conexiones salientes de un nodo específico.
     */
    public List<Connection> getConnectionsFrom(Node node) {
        List<Connection> connectedNodes = connections.stream()
                .filter(c -> c.getFromNode().equals(node))
                .toList();
        System.out.println(connectedNodes);
        return connectedNodes;
    }

    public List<Connection> getConnectionsTo(Node node) {
        List<Connection> connectedNodes = connections.stream()
                .filter(c -> c.getToNode().equals(node))
                .toList();
        System.out.println(connectedNodes);
        return connectedNodes;
    }

    public void resolveConnections() {
        if (connections == null) return;
        for (Connection c : connections) {
            c.resolve(this);
        }
    }

    // Setters sincronizados con las llaves del JSON enviado desde el cliente
    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNodes(List<Node> nodes) { this.nodes = nodes; }
    public void setConnections(List<Connection> connections) { this.connections = connections; }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public List<Node> getNodes() { return nodes; }
    public List<Connection> getConnections() { return connections; }

    public Node getStartNode() {
        return nodes.stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .findFirst()
                .orElseThrow();
    }

    //Recupera un nodo específico por su identificador único dentro del grafo.
    public Node getNodeById(String id) {
        return nodes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Nodo no encontrado: " + id)
                );
    }
}