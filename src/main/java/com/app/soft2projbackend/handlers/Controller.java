package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.Flow;
import com.app.soft2projbackend.model.ExecutionContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "http://192.168.5.2:3000"
})
public class Controller {

    private final Engine engine;
    private final Validator validator;

    public Controller(Engine engine, Validator validator) {
        this.engine = engine;
        this.validator = validator;
    }

    /**
     * Recepción y ejecución de workflows. Orquesta la validación previa a la ejecución.
     */
    @PostMapping("/run")
    public ExecutionContext runWorkflow(@RequestBody Flow workflow) {

        // Fase de validación de reglas de negocio y estructura de datos
        validator.validate(workflow);

        // Fase de procesamiento y ejecución del motor
        return engine.run(workflow);
    }

    // Command ping pong
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @PostMapping("/debug")
    public ExecutionContext debugWorkflow(@RequestBody Flow flow) {
        System.out.println(flow.getNodes().getFirst().getClass());
        return engine.run(flow);
    }

    @GetMapping("/askdebug")
    public String debugGet() {
        return "Use POST with JSON body";
    }
}