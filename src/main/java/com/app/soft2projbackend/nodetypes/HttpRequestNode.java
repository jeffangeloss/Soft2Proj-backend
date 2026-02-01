package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.TipoNodo;

public class HttpRequestNode extends Node {
    private String url;

    public HttpRequestNode() {
        this.type = TipoNodo.HTTP_REQUEST;
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        System.out.println("Calling URL: " + url);

        int statusCode = 200; // simulado
        context.put("httpStatus", statusCode);
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
