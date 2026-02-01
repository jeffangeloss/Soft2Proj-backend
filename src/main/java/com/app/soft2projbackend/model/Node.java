package com.app.soft2projbackend.model;

import com.app.soft2projbackend.nodetypes.*;
import com.fasterxml.jackson.annotation.*;

/**
 * Clase abstracta base para todos los componentes del grafo.
 * como START o COMMAND basándose en la propiedad 'type' del JSON.
 */
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
public abstract class Node {
    protected String id; // Identificador único del nodo sincronizado con el frontend
    protected String name;
    protected TipoNodo type;
    protected PoliticaError politica;
    protected String message;

    protected Node() {} // Constructor necesario para la deserialización de frameworks

    // Getters y Setters para inyección de datos desde el controlador
    public String getId() { return id; }
    public String getName() { return name; }
    public TipoNodo getType() { return type; }
    public PoliticaError getErrorPolicy() { return politica; }
    public String getMessage() { return message; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(TipoNodo type) { this.type = type; }
    public void setPolitica(PoliticaError politica) { this.politica = politica; }
    public void setMessage(String message) { this.message = message; }

    /**
     * Definición del contrato de ejecución que cada nodo concreto debe implementar.
     */
    public abstract void execute(ExecutionContext context) throws Exception;
}