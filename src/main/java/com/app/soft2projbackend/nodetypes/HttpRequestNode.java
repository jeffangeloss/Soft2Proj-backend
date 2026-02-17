package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.handlers.*;
import com.app.soft2projbackend.model.*;

import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class HttpRequestNode extends Node {
    private final String url = "https://script.googleusercontent.com/macros/echo?user_content_key=AY5xjrQQjIoTgx84QHtulmkYEcK1jgEaAKqyOPhq0hkZf3Bqe44yOHg1pIV6qBcaL7KhVyIeObUzLnEWWAZviH5GQFdUaw7jjjx8GXUHtP9oQ6h4pAlr7fZPGeP7zL2mfijshw3lEF9H6d7xFJfeFUYQFPzip2wvOTDAk5dO4FiH-1nKfhZAeQenF7ra0LlVkykgqinigoxAZnBqrTjmGG0JLYJWksMZU2pI6tQyuE20qMdRlseaXvOij5TYp4YYdrDg-DjcYTVS80a0qatacINO2V85vWwZEOGjl5Cz0cY_&lib=MwotjRmUun0RLlzJNoicmGhJptMVnD4LO";
    private String method;
    private PoliticaError politica;
    private int timeout = 5000;
    private int attempts = 3;
    private HttpHelper helper;
    private int index;


    public HttpRequestNode() {
        this.type = TipoNodo.HTTP_REQUEST;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
        this.helper = HttpHelper.getHelper();
    }

    public String getUrl() {
        return this.url;
    }
    public PoliticaError getPolitica() {
        return this.politica;
    }
    public int getIndex() {
        return this.index;
    }

    // setters usados por Jackson
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
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void execute(ExecutionContext context) {

        HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        boolean success = false;
        String gamename = "No se encontró";

        for (int i = 1; i <= attempts; i++) {
            try {
                HttpRequest.Builder builder = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofMillis(timeout));

                // Elegir metodo según JSON
                if (method == null || method.equalsIgnoreCase("GET")) {
                    builder.GET();
                } else if (method.equalsIgnoreCase("POST")) {
                    builder.POST(HttpRequest.BodyPublishers.noBody());
                } else {
                    success = false;
                    break;
                }

                HttpRequest request = builder.build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    String jsonResponse = response.body();
                    gamename = helper.saveData(jsonResponse, context, this);
                    success = true;
                    break;
                }

            } catch (Exception e) {
                success = false;
            }
        }

        // Guardar el resultado en el contexto
            context.put("GameName" + id, gamename);
            context.put("conditionResult" + id, success);

    }
}