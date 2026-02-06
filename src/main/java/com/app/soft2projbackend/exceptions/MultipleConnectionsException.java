package com.app.soft2projbackend.exceptions;

public class MultipleConnectionsException extends RuntimeException{
    public MultipleConnectionsException() {
        super("Más de una conexion al Nodo");
    }
}