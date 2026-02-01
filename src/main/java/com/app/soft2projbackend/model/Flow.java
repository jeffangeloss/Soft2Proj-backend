package com.app.soft2projbackend.model;

import java.util.*;

/**
 * Representa la topología completa de un workflow, incluyendo sus nodos y aristas (conexiones).
 */
public class Flow {
    private String id;
    private String name;
    private List<Node> nodes = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    public Flow() {}

    /**
     * Filtra y retorna todas las conexiones salientes de un nodo específico.
     */
    public List<Connection> getConnectionsFrom(String nodeId) {
        return connections.stream()
                .filter(c -> c.getFromNodeId().equals(nodeId))
                .toList();
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

    /**
     * Busca el nodo de entrada principal para iniciar la ejecución del motor.
     */
    public Node getStartNode() {
        return nodes.stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Recupera un nodo específico por su identificador único dentro del grafo.
     */
    public Node getNodeById(String id) {
        return nodes.stream()
                .filter(n -> n.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Nodo no encontrado: " + id)
                );
    }
}