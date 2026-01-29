package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.ExecutionContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workflows")
@CrossOrigin(origins = "http://localhost:3000")
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/run")
    public ExecutionContext runWorkflow(@RequestBody String workflowJson) throws Exception {
        return workflowService.runAndSave(workflowJson);
    }
}