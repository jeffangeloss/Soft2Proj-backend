package com.app.soft2projbackend.nodetypes;

import com.app.soft2projbackend.exceptions.InvalidArgumentException;
import com.app.soft2projbackend.model.*;
import tools.jackson.databind.*;

import java.net.URI;
import java.net.http.*;

public class HttpRequestNode extends Node {
    private String url = "https://script.google.com/macros/s/AKfycbyM6p40ois-vNNIbSBBXCcagOxi2Zp4NR6NKXUBYfaXg4HdFZR5XIAxXLhEr4Txg3goQg/exec";
    private String inputKey;   // ej: "gameId"

    public HttpRequestNode() {
        this.type = TipoNodo.HTTP_REQUEST;
        this.politica = PoliticaError.CONTINUE_ON_FAIL;
        this.message = "Obten la descripcion de tu juego";
    }

    public void getUrl(String url) {
        this.url = url;
    }

    // setters usados por Jackson
    public void setUrl(String url) {
        this.url = url;
    }

    public void setInputKey(String inputKey) {
        this.inputKey = inputKey;
    }

    @Override
    public void execute(ExecutionContext context) throws Exception {
        // Chek key au
        if (inputKey == null) {
            throw new InvalidArgumentException();
        }

        // HTTP kolplei
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // breik Json daun
        ObjectMapper mapper = new ObjectMapper();
        JsonNode gameList = mapper.readTree(response.body());

        // lufa pleiplei au
        String description = null;

        for (JsonNode game : gameList) {
            if (game.get("nombre").asString().equals(inputKey)) {
                description = game.get("descripcion").asString();
                break;
            }
        }

        if (description == null) {
            throw new InvalidArgumentException();
        }

        // Kep em klin
        context.put("gameDescription" + id, description);
    }
}
