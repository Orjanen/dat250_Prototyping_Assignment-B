package com.example.analytics.mongodb.handler.util;


import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

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

    public static JsonObject convertToJsonObject(String json) {
        return gson.fromJson(json,JsonObject.class);
    }


}
