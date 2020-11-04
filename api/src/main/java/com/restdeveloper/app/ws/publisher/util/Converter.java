package com.restdeveloper.app.ws.publisher.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Converter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Converter.class);

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

    private Converter() {
    }

    public static String convertPollToJson(PollEntity poll) {
        LOGGER.info("Converting PollEntity to Json-string...");

        JsonObject json = new JsonObject();
        json.addProperty("type", "poll");
        json.addProperty("jpaId", poll.getPollId());
        json.addProperty("pollName", poll.getPollName());
        json.addProperty("optionOne", poll.getOptionOne());
        json.addProperty("optionTwo", poll.getOptionTwo());
        json.addProperty("optionOneVotes", poll.getOptionOneVotes());
        json.addProperty("optionTwoVotes", poll.getOptionTwoVotes());
        json.addProperty("isStillActive", !poll.isFinished());

        LOGGER.debug("Done converting");
        return gson.toJson(json);
    }

    public static String convertVoteToJson(VoteEntity vote) {
        LOGGER.info("Converting VoteEntity to Json-string...");

        JsonObject json = new JsonObject();
        json.addProperty("type", "vote");
        json.addProperty("jpaId", vote.getPollEntity().getPollId());
        json.addProperty("optionOneVotes", vote.getOption1Count());
        json.addProperty("optionTwoVotes", vote.getOption2Count());
        json.addProperty("isStillActive", !vote.getPollEntity().isFinished());

        LOGGER.debug("Done converting");
        return gson.toJson(json);
    }

}
