package com.app.soft2projbackend.handlers;

import com.app.soft2projbackend.model.*;
import tools.jackson.databind.*;

public class JsonHttpLoop {
    private static JsonHttpLoop helper = null;
    private final ObjectMapper mapper = new ObjectMapper();
    private JsonNode root;

    private JsonHttpLoop() {}

    public static JsonHttpLoop getHelper() {
        if (helper == null) {
            helper = new JsonHttpLoop();
        }
        return helper;
    }

    public String saveData(String json, ExecutionContext context, Node node) {
        try {
            JsonNode root = mapper.readTree(json);

            // El JSON es un array
            if (!root.isArray()) {
                return "JSON no es un array";
            }

            // Buscar el juego
            for (JsonNode game : root) {
                    context.put("gameId"+node.getId(), game.get("id").asInt());
                    context.put("gameName"+node.getId(), game.get("nombre").asText());
                    context.put("gameDescription"+node.getId(), game.get("descripcion").asText());
                    context.put("gameImage"+node.getId(), game.get("imagen").asText());

                    JsonNode category = game.get("categoria");
                    if (category != null && category.isObject()) {
                        context.put("gameCategory", category.get("nombre").asText());
                    }
                    return "Guardado";
            }

            return "Información Guardada";

        } catch (Exception e) {
            return "Error al procesar JSON";
        }
    }
}