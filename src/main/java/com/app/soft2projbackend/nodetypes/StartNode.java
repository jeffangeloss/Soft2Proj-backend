package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;

public class StartNode extends Node {
    public StartNode(String id, String name, PoliticaError politica) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.START;
        this.politica = politica;
        this.message = "Workflow Iniciado";
    }

    @Override
    public void execute(ExecutionContext context) {
        context.put("started", true); //Crea variable de inicio y la inserta en ExcecutionContext
        System.out.println("Workflow iniciado");
    }
}
