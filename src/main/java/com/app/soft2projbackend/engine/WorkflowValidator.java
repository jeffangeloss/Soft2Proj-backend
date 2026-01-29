package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.enums.TipoNodo;

public class WorkflowValidator {
    public void validate(WorkflowMap workflowMap) {

        long startCount = workflowMap.getNodos().values().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .count();

        if (startCount != 1) {
            throw new IllegalStateException("Workflow debe tener exactamente un START");
        }

        // Validaciones más avanzadas luego
    }
}