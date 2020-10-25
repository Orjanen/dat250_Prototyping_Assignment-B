package com.example.analytics.handler.util;


import com.example.analytics.ui.model.PollModel;
import com.example.analytics.ui.model.VoteModel;
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

    public static VoteModel convertToVoteModel(JsonObject json) {
        return gson.fromJson(json,VoteModel.class);
    }

    public static PollModel convertToPollModel(JsonObject json) {
        return gson.fromJson(json,PollModel.class);
    }

}
