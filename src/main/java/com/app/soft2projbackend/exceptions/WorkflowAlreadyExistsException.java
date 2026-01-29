package com.app.soft2projbackend.exceptions;

public class WorkflowAlreadyExistsException extends Exception{
    public WorkflowAlreadyExistsException(String name) {
        super("Ya existe un workflow con nombre: " + name);
    }
}
