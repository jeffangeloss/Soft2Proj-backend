package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.exceptions.WorkflowAlreadyExistsException;
import com.app.soft2projbackend.exceptions.WorkflowNotFoundException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class WorkflowService
{

    private final Map<String, WorkflowMap> workflows = new HashMap<>();

    // Crear workflow
    public WorkflowMap createWorkflow(String name) throws WorkflowAlreadyExistsException {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El workflow debe tener nombre");
        }

        boolean exists = workflows.values().stream()
                .anyMatch(w -> w.getName().equalsIgnoreCase(name));

        if (exists) {
            throw new WorkflowAlreadyExistsException(name);
        }

        WorkflowMap workflow = new WorkflowMap();
        workflows.put(workflow.getId(), workflow);
        return workflow;
    }

    //Obtener workflow
    public WorkflowMap getWorkflow(String id) {
        WorkflowMap workflow = workflows.get(id);

        if (workflow == null) {
            throw new WorkflowNotFoundException(id);
        }

        return workflow;
    }

    //Listar workflows
    public List<WorkflowMap> getAllWorkflows() {
        return new ArrayList<>(workflows.values());
    }
}
