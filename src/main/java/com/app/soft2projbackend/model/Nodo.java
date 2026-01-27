package com.app.soft2projbackend.model;

import com.app.soft2projbackend.enums.*;

import java.util.UUID;

public abstract class Nodo {
    protected String id;
    protected String name;
    protected TipoNodo tipo;
    protected PoliticaError politica;

    protected Nodo(String name, TipoNodo type) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.tipo = type;
        this.politica = PoliticaError.STOP_ON_FAIL;
    }

    public String getId() {
        return id;
    }

    public TipoNodo getType() {
        return tipo;
    }

    public PoliticaError getErrorPolicy() {
        return politica;
    }

    public void setPoliticaError(PoliticaError politica) {
        this.politica = politica;
    }

    /**
     * Método clave del runtime
     */
    public abstract void execute(ExecutionContext context) throws Exception;
}
