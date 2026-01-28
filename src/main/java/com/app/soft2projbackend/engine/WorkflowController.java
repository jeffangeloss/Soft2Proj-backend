package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.model.ExecutionContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "http://localhost:3000")

public class WorkflowController {

    private final WorkflowEngine workflowEngine;

    public WorkflowController(WorkflowEngine engine) {
        this.workflowEngine = engine;
    }

    @PostMapping("/run")
    public ExecutionContext runWorkflow(@RequestBody Workflow workflow) {
        return workflowEngine.run(workflow);
    }
}