package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.model.*;
import tools.jackson.databind.*;

import java.net.URI;
import java.net.http.*;

public class HttpRequestNode extends Node {
    private String url;
    private String method;
    private PoliticaError politica;
    private int timeout;
    private int attempts;


    public HttpRequestNode() {
        this.type = TipoNodo.HTTP_REQUEST;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
    }

    public void getUrl(String url) {
        this.url = url;
    }
    public PoliticaError getPolitica() {
        return politica;
    }

    // setters usados por Jackson
    public void setUrl(String url) {
        this.url = url;
    }
    public void setMethod(String method) {
        this.method = method;
    }
    public void setPolitica(String politica) {
        switch (politica) {
            case "STOP" -> this.politica = PoliticaError.STOP_ON_FAIL;
            case "CONTINUE" -> this.politica = PoliticaError.CONTINUE_ON_FAIL;
            default -> this.politica = PoliticaError.STOP_ON_FAIL;
        }
    }
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        // Kep em klin
        context.put("gameDescription" + id, 1);
    }
}
