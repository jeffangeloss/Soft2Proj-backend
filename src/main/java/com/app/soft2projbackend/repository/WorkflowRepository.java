package com.app.soft2projbackend.repository;

import com.app.soft2projbackend.model.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
    void save(WorkflowEntity entity);
}