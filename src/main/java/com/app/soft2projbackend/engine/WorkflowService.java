package com.app.soft2projbackend.engine;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.repository.WorkflowEntity;
import com.app.soft2projbackend.repository.WorkflowRepository;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class WorkflowService {

    private final WorkflowRepository repository;
    private final WorkflowEngine engine;
    private final ObjectMapper objectMapper;

    public WorkflowService(WorkflowRepository repository,
                           WorkflowEngine engine,
                           ObjectMapper objectMapper) {
        this.repository = repository;
        this.engine = engine;
        this.objectMapper = objectMapper;
    }

    public ExecutionContext runAndSave(String workflowJson) throws Exception {

        // 1. Guardar en BD
        WorkflowEntity entity = new WorkflowEntity();
        entity.setWorkflowJson(workflowJson);
        repository.save(entity);

        // 2. Convertir JSON a objeto
        Workflow workflow = objectMapper.readValue(workflowJson, Workflow.class);

        // 3. Ejecutar motor
        return engine.run(workflow);
    }
}
