package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;

import java.util.Map;

public class CommandNode extends Node {
    private String message;
    private String commandType;
    private Map<String, Object> params;

    public CommandNode(String id, String name, PoliticaError politica) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.COMMAND;
        this.politica = politica;
    }

    @Override
    public void execute(ExecutionContext context) {
        System.out.println("Command: " + message);
        context.put("lastCommand", message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
