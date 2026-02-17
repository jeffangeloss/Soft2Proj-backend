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

                    context.put("game" + game.get("id").asString(), game.get("nombre").asText());

                    JsonNode category = game.get("categoria");
                    if (category != null && category.isObject()) {
                        context.put("gameCategory", category.get("nombre").asText());
                    }

                    return "Guardado";
                }
            }

            return "Juego no encontrado";

        } catch (Exception e) {
            return "Error procesando JSON";
        }
    }
}