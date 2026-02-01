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

    public void validate(Flow flow) {
        List<Node> startNodes = flow.getNodos().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .toList();

        if (startNodes.size() != 1) {
            throw new IllegalStateException("El workflow debe tener exactamente un nodo START.");
        }
        checkConnectivity(flow, startNodes.get(0));
    }

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

        if (visitedIds.size() < flow.getNodos().size()) {
            List<String> allIds = flow.getNodos().stream().map(Node::getId).collect(Collectors.toList());
            allIds.removeAll(visitedIds);
            throw new IllegalStateException("Nodos no alcanzables detectados (flujo roto): " + allIds);
        }
    }
}