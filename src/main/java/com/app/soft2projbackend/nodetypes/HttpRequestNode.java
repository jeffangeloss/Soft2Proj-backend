package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.model.*;
import tools.jackson.databind.*;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class HttpRequestNode extends Node {
    private String url;
    private String method;
    private PoliticaError politica;
    private int timeout = 5000;
    private int attempts = 3;


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
    public void execute(ExecutionContext context) {

        HttpClient client = HttpClient.newHttpClient();
        boolean success = false;

        for (int i = 1; i <= attempts; i++) {
            try {
                HttpRequest.Builder builder = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofMillis(timeout));

                // Elegir metodo según JSON
                if (method.equalsIgnoreCase("GET")) {
                    builder.GET();
                } else if (method.equalsIgnoreCase("POST")) {
                    builder.POST(HttpRequest.BodyPublishers.noBody());
                } else {
                    success = false;
                    break;
                }

                HttpRequest request = builder.build();

                HttpResponse<Void> response =
                        client.send(request, HttpResponse.BodyHandlers.discarding());

                if (response.statusCode() == 200) {
                    success = true;
                    break;
                }

            } catch (Exception e) {
                success = false;
            }
        }

        // Guardar el resultado en el contexto
        context.put("conditionResult" + id, success);
    }
}
