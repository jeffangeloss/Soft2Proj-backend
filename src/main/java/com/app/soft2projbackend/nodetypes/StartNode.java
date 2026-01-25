package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.ExecutionContext;
import com.app.soft2projbackend.Nodo;
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
