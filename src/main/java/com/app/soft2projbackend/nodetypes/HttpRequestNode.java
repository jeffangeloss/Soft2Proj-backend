package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.ExecutionContext;
import com.app.soft2projbackend.Nodo;
import com.app.soft2projbackend.enums.TipoNodo;

public class HttpRequestNode extends Nodo {
    private String url;
    private String method;

    public HttpRequestNode(String name, String url, String method) {
        super(name, TipoNodo.HTTP_REQUEST);
        this.url = url;
        this.method = method;
    }

    @Override
    public void execute(ExecutionContext context) {
        // Por ahora mock
        System.out.println("Ejecutando HTTP " + method + " a " + url);

        // Simular respuesta
        context.put("httpResponse", "200 OK");
    }
}
