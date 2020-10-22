package com.restdeveloper.app.ws.publisher.util;

import com.google.gson.*;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;

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

    public static String convertPollToJson(PollEntity poll) {
        System.out.println("Converting PollEntity to Json-string...");
        JsonObject json = new JsonObject();
        json.addProperty("type", "poll");
        json.addProperty("jpaId", poll.getPollId());
        json.addProperty("pollName", poll.getPollName());
        json.addProperty("optionOne", poll.getOptionOne());
        json.addProperty("optionTwo", poll.getOptionTwo());
        return gson.toJson(json);
    }

    public static String convertVoteToJson(VoteEntity vote) {
        System.out.println("Converting VoteEntity to Json-string...");
        JsonObject json = new JsonObject();
        json.addProperty("type", "vote");
        json.addProperty("jpaId", vote.getPollEntity().getPollId());
        json.addProperty("optionOneVotes", vote.getOption1Count());
        json.addProperty("optionTwoVotes", vote.getOption2Count());
        return gson.toJson(json);
    }

}
