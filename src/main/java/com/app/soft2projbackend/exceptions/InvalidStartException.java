package com.app.soft2projbackend.exceptions;

public class InvalidStartException extends RuntimeException{
    public InvalidStartException() {
        super("Numero de Nodos Start no válido");
    }
}
