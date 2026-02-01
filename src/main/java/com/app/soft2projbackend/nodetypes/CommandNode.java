package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.exceptions.InvalidKeyException;
import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.steprun.StepRun;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CommandNode extends Node {
    private String commandType;
    private String key;
    private String value;
    private String command;
    private List<String> arguments;
    private Map<String, String> environment;
    private String workingDirectory;
    private long timeout = 5000;
    private String outputKey;


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
    public void execute(ExecutionContext context, StepRun stepRun) {
        stepRun.markStart();

        System.out.println("Command: " + message);
        if (key == null) throw new InvalidKeyException();
        if (value == null) throw new InvalidArgumentException();
        if ("SET_VARIABLE".equalsIgnoreCase(commandType)) {
            context.put(key, value);
            stepRun.setOutput("Variable '" + key + "' set");
        }
        if ("LOG".equalsIgnoreCase(commandType)) {
            System.out.println(key + ": " + value);
            stepRun.setOutput(String.valueOf(key));
        }
        if ("INCREMENT".equalsIgnoreCase(commandType)) {
            Object currentObj = context.get(key);

            if (currentObj == null) {
                throw new IllegalStateException("Variable not found: " + key);
            }

            int current = Integer.parseInt(currentObj.toString());
            int inc = Integer.parseInt(value);

            context.put(key, current + inc);
            stepRun.setOutput("Incremented to " + inc);
        }
        if("EXECUTE".equalsIgnoreCase(commandType)){
            try {
                executeSystemCommand(context,stepRun);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void executeSystemCommand(ExecutionContext context, StepRun stepRun)
            throws Exception {

        if (command == null || command.isBlank()) {
            throw new IllegalStateException("Command is required");
        }

        List<String> cmd = new ArrayList<>();
        cmd.add(command);
        if (arguments != null) {
            cmd.addAll(arguments);
        }

        ProcessBuilder pb = new ProcessBuilder(cmd);

        if (workingDirectory != null) {
            pb.directory(new File(workingDirectory));
        }

        if (environment != null) {
            pb.environment().putAll(environment);
        }

        Process process = pb.start();

        boolean finished = process.waitFor(timeout, TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new RuntimeException("Command timeout after " + timeout + "ms");
        }

        String stdout = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String stderr = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);

        stepRun.setOutput(stdout);

        if (outputKey != null) {
            context.put(outputKey, stdout);
        }

        if (process.exitValue() != 0) {
            throw new RuntimeException(stderr.isBlank() ? "Command failed" : stderr);
        }
    }
}