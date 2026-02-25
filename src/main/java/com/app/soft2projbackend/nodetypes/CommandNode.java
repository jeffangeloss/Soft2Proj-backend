package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.*;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.steprun.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandNode extends Node {
    private String command;
    private String key;

    public CommandNode() {
        this.type = TipoNodo.COMMAND;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
    }
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        this.key = "output" + id;
    }

    public String getCommand() {
        return this.command;
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        StepRun reloj = new StepRun(this.id);
        reloj.markStart();
        if (command == null || command.isBlank()) {
            reloj.markEnd(StepStatus.FAILED);
            throw new InvalidArgumentException();
        }
        try {
            ProcessBuilder pb = new ProcessBuilder();
            pb.directory(new File("comandos"));
            pb.command("cmd.exe", "/c", command);

            Process process = pb.start();
            boolean finished = process.waitFor(10, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                reloj.setError("Timeout: El comando tardó demasiado");
                reloj.markEnd(StepStatus.FAILED); //
                throw new RuntimeException("Command timeout");
            }

            String output = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            System.out.println("Exit code: " + process.exitValue());
            System.out.println("STDOUT: " + output);
            System.out.println("STDERR: " + error);
            if (process.exitValue() == 0) {
                String[] lines = output.split("\\R"); // separa por saltos de línea
                String lastLine = lines.length > 0 ? lines[lines.length - 1].trim() : "";
                context.put(key, lastLine);
                context.put("conditionResult" + id, true);
                reloj.setOutput(output);
                reloj.markEnd(StepStatus.SUCCESS);
            } else {
                reloj.setError(error);
                reloj.markEnd(StepStatus.FAILED);
                context.put(key, false);
                context.put("conditionResult" + id, false);
                if (this.politica == PoliticaError.STOP_ON_FAIL) {
                    throw new RuntimeException("Error en comando: " + error);
                }
            }

        } catch (Exception e) {
            context.put("conditionResult" + id, false);
            reloj.setError(e.getMessage());
            reloj.markEnd(StepStatus.FAILED);
            throw e;
        }
    }
}