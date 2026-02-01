package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.TipoNodo;

public class StartNode extends Node {
    public StartNode(String name) {
        this.name = name;
        this.type = TipoNodo.START;
    }

    @Override
    public void execute(ExecutionContext context) {
        // No hace nada
        System.out.println("Workflow iniciado");
    }
}
