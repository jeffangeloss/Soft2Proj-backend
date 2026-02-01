package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.TipoNodo;

public class CommandNode extends Node {

    private String message;

    public CommandNode() {
        this.type = TipoNodo.COMMAND;
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
