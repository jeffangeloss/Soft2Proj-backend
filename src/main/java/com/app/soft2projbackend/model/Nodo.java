package com.app.soft2projbackend.model;

import com.app.soft2projbackend.nodetypes.*;
import com.fasterxml.jackson.annotation.*;

import java.util.UUID;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StartNode.class, name = "START"),
        @JsonSubTypes.Type(value = CommandNode.class, name = "COMMAND"),
        @JsonSubTypes.Type(value = ConditionalNode.class, name = "CONDITIONAL"),
        @JsonSubTypes.Type(value = HttpRequestNode.class, name = "HTTP")
})
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
