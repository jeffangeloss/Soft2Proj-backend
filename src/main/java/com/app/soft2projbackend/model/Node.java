package com.app.soft2projbackend.model;

import com.app.soft2projbackend.nodetypes.*;
import com.app.soft2projbackend.steprun.StepRun;
import com.fasterxml.jackson.annotation.*;

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
    protected String id;
    protected String name;
    protected TipoNodo type;
    protected PoliticaError politica;
    protected String message;

    protected Node() {} // Constructor vacío necesario

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

    public abstract void execute(ExecutionContext context) throws Exception;
}