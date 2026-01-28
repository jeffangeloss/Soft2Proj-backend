package com.app.soft2projbackend.repository;

import com.app.soft2projbackend.model.Workflow;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends CrudRepository<Workflow, Long> {
}