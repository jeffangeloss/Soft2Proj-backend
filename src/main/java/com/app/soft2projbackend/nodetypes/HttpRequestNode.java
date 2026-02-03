package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.PoliticaError;
import com.app.soft2projbackend.model.TipoNodo;
import com.app.soft2projbackend.steprun.StepRun;

public class HttpRequestNode extends Node {
    private String url;

    public HttpRequestNode(String id, String name, PoliticaError politica, String message) {
        this.id = id;
        this.name = name;
        this.type = TipoNodo.HTTP_REQUEST;
        this.politica = politica;
        this.message = message;
    }

    @Override
    public void execute(ExecutionContext context, StepRun stepRun) throws Exception {
        System.out.println("Calling URL: " + url);

        int statusCode = 200; // simulado
        context.put("httpStatus", statusCode);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
