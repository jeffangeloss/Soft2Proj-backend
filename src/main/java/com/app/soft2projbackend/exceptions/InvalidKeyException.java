package com.app.soft2projbackend.exceptions;

public class InvalidKeyException extends RuntimeException{
    public InvalidKeyException() {
        super("Tipo de dato no válido");
    }
}
