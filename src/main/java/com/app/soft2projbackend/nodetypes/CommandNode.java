package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.*;
import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.steprun.StepRun;
import com.app.soft2projbackend.steprun.StepStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CommandNode extends Node {
    // Cambiamos 'int value' por 'String command' para recibir el comando Bash/Terminal
    private String command;
    private String key;

    public CommandNode() {
        this.type = TipoNodo.COMMAND;
        this.politica = PoliticaError.CONTINUE_ON_FAIL; //
    }

    // Setters para que Jackson pueda inyectar los datos desde el JSON
    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public void setId(String id) {
        this.id = id;
        this.key = "output" + id; // Llave donde guardaremos el resultado en el contexto
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        StepRun reloj = new StepRun(this.id); //
        reloj.markStart(); //

        if (command == null || command.isBlank()) {
            reloj.markEnd(StepStatus.FAILED); //
            throw new InvalidArgumentException(); //
        }

        try {
            // Ejecución a nivel de sistema operativo
            ProcessBuilder pb = new ProcessBuilder();

            // Determinar el shell según el OS (Bash para Mac/Linux, CMD para Windows)
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                pb.command("cmd.exe", "/c", command);
            } else {
                pb.command("bash", "-c", command);
            }

            Process process = pb.start();

            // Timeout de seguridad (5 segundos) para evitar que el backend se cuelgue
            boolean finished = process.waitFor(5, TimeUnit.SECONDS);

            if (!finished) {
                process.destroyForcibly();
                reloj.setError("Timeout: El comando tardó demasiado"); //
                reloj.markEnd(StepStatus.FAILED); //
                throw new RuntimeException("Command timeout");
            }

            // Captura de la salida (stdout)
            String output = new BufferedReader(
                    new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            // Captura de errores (stderr)
            String error = new BufferedReader(
                    new InputStreamReader(process.getErrorStream(), StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));

            if (process.exitValue() == 0) {
                context.put(key, output); // Guardamos la salida en el contexto
                reloj.setOutput(output); //
                reloj.markEnd(StepStatus.SUCCESS); //
            } else {
                reloj.setError(error); //
                reloj.markEnd(StepStatus.FAILED); //
                // Si la política es STOP, lanzamos excepción
                if (this.politica == PoliticaError.STOP_ON_FAIL) {
                    throw new RuntimeException("Error en comando: " + error);
                }
            }

        } catch (Exception e) {
            reloj.setError(e.getMessage()); //
            reloj.markEnd(StepStatus.FAILED); //
            throw e;
        }
    }
}