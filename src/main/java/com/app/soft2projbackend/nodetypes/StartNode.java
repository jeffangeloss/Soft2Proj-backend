package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Nodo;
import com.app.soft2projbackend.enums.*;

public class StartNode extends Nodo {
    public StartNode(String name) {
        super(name, TipoNodo.START);
    }

    @Override
    public void execute(ExecutionContext context) {
        // No hace nada
        System.out.println("Workflow iniciado");
    }
}
