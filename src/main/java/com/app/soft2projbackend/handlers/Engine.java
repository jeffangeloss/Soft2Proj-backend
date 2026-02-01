package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Engine {

    /**
     * Punto de entrada para la ejecución secuencial del flujo de trabajo.
     * Gestiona el ciclo de vida de la ejecución y el manejo de estados mediante el contexto.
     */
    public ExecutionContext run(Flow flow) {

        ExecutionContext context = new ExecutionContext();
        // Obtención del nodo inicial definido por el tipo START para comenzar la navegación
        Node current = flow.getStartNode();

        while (current != null) {
            try {
                // Ejecución del nodo actual y actualización del contexto de datos
                current.execute(context);
                current = getNextNode(flow, current, context);
            } catch (Exception e) {
                // Evaluación de la política de error para determinar si se aborta o continúa el flujo
                if (current.getErrorPolicy() == PoliticaError.STOP) {
                    throw new RuntimeException(e);
                }
                current = getNextNode(flow, current, context);
            }
        }
        return context;
    }

    /**
     * Lógica de navegación entre nodos basada en tipos y condiciones de salida.
     */
    private Node getNextNode(Flow flow, Node current, ExecutionContext context) {

        List<Connection> outs = flow.getConnectionsFrom(current.getId());

        // Resolución de bifurcaciones para nodos de tipo CONDITIONAL
        if (current.getType() == TipoNodo.CONDITIONAL) {
            Boolean result = (Boolean) context.get("conditionResult");
            return outs.stream()
                    .filter(c -> c.matches(result))
                    .findFirst()
                    .map(c -> flow.getNodeById(c.getToNodeId()))
                    .orElse(null);
        }

        // Retorno del siguiente nodo para estructuras lineales
        return outs.isEmpty() ? null : flow.getNodeById(outs.get(0).getToNodeId());
    }
}