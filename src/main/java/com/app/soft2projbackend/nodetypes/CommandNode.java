package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.*;
import com.app.soft2projbackend.handlers.*;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.steprun.StepRun;
import java.util.*;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class CommandNode extends Node {
    private String commandType;
    private String key;
    private int value;

    /*
    private String command;
    private List<String> arguments;
    private Map<String, String> environment;
    private String workingDirectory;
    private String outputKey;
    */

    public CommandNode() {
        this.type = TipoNodo.COMMAND;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
        this.key = "operation" + id;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public void setValue(int value) {
        this.value = value;
    }
/*
    public void setCommand(String command) {
        this.command = command;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public void setEnvironment(Map<String, String> environment) {
        this.environment = environment;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public void setOutputKey(String outputKey) {
        this.outputKey = outputKey;
    }
*/

    @Override
    public void execute(ExecutionContext context) {
        StepRun reloj = new StepRun(this.id);
        reloj.markStart();

        System.out.println("Command: " + commandType);
        if (key == null) throw new InvalidKeyException();
        if ("SQUARE".equalsIgnoreCase(commandType)) {
            long expoFunc = MathFunctions.square(value);
            context.put(key, expoFunc);
            reloj.setOutput("Variable: " + key + " set");
        }
        if ("FACTORIAL".equalsIgnoreCase(commandType)) {
            long expoFunc = MathFunctions.factorial(value);
            context.put(key, expoFunc);
            reloj.setOutput("Variable: " + key + " set");
        }
        if ("EXPONENTIAL".equalsIgnoreCase(commandType)) {
            long expoFunc = MathFunctions.expo(value);
            context.put(key, expoFunc);
            reloj.setOutput("Variable: " + key + " set");
        }
        if ("ADD".equalsIgnoreCase(commandType)) {
            long expoFunc = MathFunctions.add(value);
            context.put(key, expoFunc);
            reloj.setOutput("Variable: " + key + " set");
        }
    }
/*
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

        long timeout = 5000;
        boolean finished = process.waitFor(timeout, TimeUnit.MILLISECONDS);
        if (!finished) {
            process.destroyForcibly();
            throw new RuntimeException("Command timeout after " + timeout + "ms");
        }

        String stdout = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        String stderr = new String(process.getErrorStream().readAllBytes(), StandardCharsets.UTF_8);

        stepRun.setOutput(stdout); // set output en la variable

        if (outputKey != null) {
            context.put(outputKey, stdout);
        }

        if (process.exitValue() != 0) {
            throw new RuntimeException(stderr.isBlank() ? "Command failed" : stderr);
        }
    } */
}