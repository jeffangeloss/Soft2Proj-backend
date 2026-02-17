package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.*;
import com.app.soft2projbackend.nodetypes.HttpRequestNode;
import tools.jackson.databind.*;

public class HttpHelper {
    private static HttpHelper helper = null;
    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;

    private HttpHelper() {}

    public static HttpHelper getHelper() {
        if (helper == null) {
            helper = new HttpHelper();
        }
        return helper;
    }

    public String saveData(String json, ExecutionContext context, HttpRequestNode node) {
        try {
            JsonNode root = mapper.readTree(json);

            if (!root.isArray()) {
                return "JSON no es un array";
            }

            int targetId = node.getIndex();

            for (JsonNode game : root) {

                if (game.has("id") && game.get("id").asInt() == targetId) {

                    //context.put("game" + game.get("id").asText(), game.get("nombre").asText());
                    return game.get("nombre").asString();
                }
            }

            return "Juego no encontrado";

        } catch (Exception e) {
            return "Error procesando JSON";
        }
    }
}