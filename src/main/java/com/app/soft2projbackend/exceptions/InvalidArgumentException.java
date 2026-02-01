package com.app.soft2projbackend.exceptions;

public class InvalidArgumentException extends RuntimeException{
    public InvalidArgumentException() {
        super("Argumento no válido");
    }
}
