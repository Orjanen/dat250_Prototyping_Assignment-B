package com.example.analytics.handler.util;

import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    private static final Gson gson = new Gson();

    private Converter() {
    }

    public static JsonObject convertToJsonObject(String message) {
        LOGGER.debug("Converting String to JsonObject: {}", message);
        return gson.fromJson(message, JsonObject.class);
    }

    public static RabbitVoteModel convertToVoteModel(String message) {
        LOGGER.debug("Converting String to VoteModel: {}", message);
        return gson.fromJson(message, RabbitVoteModel.class);
    }

    public static RabbitPollModel convertToPollModel(String message) {
        LOGGER.debug("Converting String to PollModel: {}", message);
        return gson.fromJson(message, RabbitPollModel.class);
    }

    public static boolean isValidJson(String message) {
        try {
            gson.fromJson(message,Object.class);
            return true;
        } catch (JsonSyntaxException e) {
            LOGGER.error("Received message is not valid Json-string", e);
            return false;
        }
    }

}
