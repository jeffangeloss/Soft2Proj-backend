package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.model.ExecutionContext;
import com.app.soft2projbackend.model.Node;
import com.app.soft2projbackend.model.TipoNodo;

public class HttpRequestNode extends Node {
    private String method;
    private String url;
    private String responseKey;

    public HttpRequestNode(String name, String method, String url, String responseKey) {
        this.name = name;
        this.type = TipoNodo.HTTP_REQUEST;
        this.method = method;
        this.url = url;
        this.responseKey = responseKey;
    }

    @Override
    public void execute(ExecutionContext context) {
        // mock de ejecución
        System.out.println("HTTP " + method + " → " + url);

        String fakeResponse = "{ \"status\": 200 }";
        context.put(responseKey, fakeResponse);
    }
}
