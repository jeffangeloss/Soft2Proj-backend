package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.WorkflowMap;
import com.app.soft2projbackend.model.ExecutionContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkflowController {

    private final WorkflowEngine workflowEngine;
    private final WorkflowValidator workflowValidator;

    public WorkflowController(WorkflowEngine workflowEngine,
                              WorkflowValidator workflowValidator) {
        this.workflowEngine = workflowEngine;
        this.workflowValidator = workflowValidator;
    }

    @PostMapping("/run")
    public ExecutionContext runWorkflow(@RequestBody WorkflowMap workflow) {

        // Validar estructura del workflow
        workflowValidator.validate(workflow);

        // Ejecutar workflow
        return workflowEngine.run(workflow);
    }
}