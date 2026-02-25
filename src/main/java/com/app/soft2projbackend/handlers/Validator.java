package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.exceptions.InvalidStartException;
import com.app.soft2projbackend.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class Validator {
    public void validate(Flow flow) {
        List<Node> startNodes = flow.getNodes().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .toList();

        if (startNodes.size() != 1) {
            throw new InvalidStartException();
        }
        checkConnectivity(flow, startNodes.get(0));
    }

    private void checkConnectivity(Flow flow, Node startNode) {
        Set<String> visitedIds = new HashSet<>();
        Queue<Node> queue = new LinkedList<>();

        queue.add(startNode);
        visitedIds.add(startNode.getId());

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            List<Connection> connections = flow.getConnectionsFrom(current);

            for (Connection conn : connections) {
                Node toNode = conn.getToNode();
                if (!visitedIds.contains(toNode.getId())) {
                    visitedIds.add(toNode.getId());
                    queue.add(toNode);
                }
            }
        }
        if (visitedIds.size() < flow.getNodes().size()) {
            List<String> allIds = flow.getNodes().stream().map(Node::getId).collect(Collectors.toList());
            allIds.removeAll(visitedIds);
            throw new IllegalStateException("Nodos no alcanzables detectados (flujo roto): " + allIds);
        }
    }
}