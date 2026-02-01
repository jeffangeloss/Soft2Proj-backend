package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.model.Connection;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Validator {

    /**
     * Valida los requisitos mínimos de estructura: un solo inicio y conectividad total.
     */
    public void validate(Flow flow) {
        // Verificación de existencia de un único nodo de inicio
        List<Node> startNodes = flow.getNodes().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .toList();

        if (startNodes.size() != 1) {
            throw new IllegalStateException("El workflow debe tener exactamente un nodo START.");
        }
        // Inicio de la validación de grafo conectado
        checkConnectivity(flow, startNodes.get(0));
    }

    /**
     * Algoritmo BFS para asegurar que no existan nodos inalcanzables o aislados.
     */
    private void checkConnectivity(Flow flow, Node startNode) {
        Set<String> visitedIds = new HashSet<>();
        Queue<String> queue = new LinkedList<>();

        queue.add(startNode.getId());
        visitedIds.add(startNode.getId());

        while (!queue.isEmpty()) {
            String currentId = queue.poll();
            List<Connection> connections = flow.getConnectionsFrom(currentId);

            for (Connection conn : connections) {
                String toId = conn.getToNodeId();
                if (!visitedIds.contains(toId)) {
                    visitedIds.add(toId);
                    queue.add(toId);
                }
            }
        }

        // Garantiza que el flujo enviado sea un solo bloque de trabajo conectado
        if (visitedIds.size() < flow.getNodes().size()) {
            List<String> allIds = flow.getNodes().stream().map(Node::getId).collect(Collectors.toList());
            allIds.removeAll(visitedIds);
            throw new IllegalStateException("Nodos no alcanzables detectados (flujo roto): " + allIds);
        }
    }
}