package com.app.soft2projbackend.repository;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "workflows")
public class WorkflowEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String workflowJson;

    private LocalDateTime createdAt = LocalDateTime.now();

    public WorkflowEntity() {}


}
