package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.*;

public class StartNode extends Node {
    public StartNode() {
        this.type = TipoNodo.START;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("started", true); //Crea variable de inicio y la inserta en ExcecutionContext
        System.out.println("Workflow iniciado");
    }
}
