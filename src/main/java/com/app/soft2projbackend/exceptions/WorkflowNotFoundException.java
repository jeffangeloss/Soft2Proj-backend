package com.app.soft2projbackend.exceptions;

public class WorkflowNotFoundException extends RuntimeException{
    public WorkflowNotFoundException(String id) {
        super("Workflow no encontrado con id: " + id);
    }
}
