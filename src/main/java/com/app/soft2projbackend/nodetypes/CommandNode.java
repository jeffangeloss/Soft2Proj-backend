package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.exceptions.InvalidKeyException;
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
        if (key == null) throw new InvalidKeyException();
        if (value == null) throw new InvalidArgumentException();
        if ("SET_VARIABLE".equalsIgnoreCase(commandType)) {
            context.put(key, value);
        }
        if ("LOG".equalsIgnoreCase(commandType)) {
            System.out.println(key + ": " + value);
        }
        if ("INCREMENT".equalsIgnoreCase(commandType)) {
            Object currentObj = context.get(key);

            if (currentObj == null) {
                throw new IllegalStateException("Variable not found: " + key);
            }

            int current = Integer.parseInt(currentObj.toString());
            int inc = Integer.parseInt(value);

            context.put(key, current + inc);
        }
    }
}
