package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.model.TipoNodo;
import org.springframework.stereotype.Service;

@Service
public class Validator {
    public void validate(Flow flow) {

        long startCount = flow.getNodos().stream()
                .filter(n -> n.getType() == TipoNodo.START)
                .count();

        if (startCount != 1) {
            throw new IllegalStateException("Workflow debe tener exactamente un START");
        }

        // Validaciones más avanzadas luego
    }
}