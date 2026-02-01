package com.app.soft2projbackend.exceptions;

public class NodeAloneException extends RuntimeException{
    public NodeAloneException() {
        super("Nodo sin fuera del flujo");
    }
}
