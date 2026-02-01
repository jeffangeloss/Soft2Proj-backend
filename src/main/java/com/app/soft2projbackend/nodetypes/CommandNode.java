package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;

import java.util.Map;

public class CommandNode extends Node {
    private String commandType;
    private String key;
    private String value;


    public CommandNode(String id, String name, PoliticaError politica, String message, String commandType, String key, String value) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.COMMAND;
        this.politica = politica;
        this.message = message;
        this.commandType = commandType;
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute(ExecutionContext context) {
        System.out.println("Command: " + message);
        if ("SET_VARIABLE".equals(commandType)) {
            context.put(key, value);
        }

    }
}
