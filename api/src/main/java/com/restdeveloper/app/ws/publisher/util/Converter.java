package com.restdeveloper.app.ws.publisher.util;

import com.google.gson.*;

public final class Converter {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

    private Converter() {
    }

    public static String convertToJson(Object object) {
        return gson.toJson(object);
    }

    public static JsonObject convertToJsonObject(Object object) {
        String jsonFormat = convertToJson(object);
        return gson.fromJson(jsonFormat,JsonObject.class);
    }

}
