package com.example.analytics.handler.util;


import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public final class Converter {

    private static final Gson gson = new Gson();

    private Converter() {
    }

    public static String convertToJson(Object object) {
        return gson.toJson(object);
    }

    public static JsonObject convertToJsonObject(String json) {
        return gson.fromJson(json,JsonObject.class);
    }

    public static RabbitVoteModel convertToVoteModel(String message) {
        return gson.fromJson(message, RabbitVoteModel.class);
    }

    public static RabbitPollModel convertToPollModel(String message) {
        return gson.fromJson(message, RabbitPollModel.class);
    }

}
