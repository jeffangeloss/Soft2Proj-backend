package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Nodo;
import com.app.soft2projbackend.enums.*;

public class CommandNode extends Nodo {
    private String command;
    private String outputKey;

    public CommandNode(String name, String command, String outputKey) {
        super(name, TipoNodo.COMMAND);
        this.command = command;
        this.outputKey = outputKey;
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        System.out.println("Ejecutando comando: " + command);

        // mock
        String output = "command output";
        context.put(outputKey, output);
    }
}
