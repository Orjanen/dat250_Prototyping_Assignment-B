package com.restdeveloper.app.ws.websocket;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.restdeveloper.app.ws.shared.dto.PollDto;
import com.restdeveloper.app.ws.shared.dto.VoteDto;

public class WebSocketMessageConverter {

    private static final Gson gson = new GsonBuilder()
            .serializeNulls()
            .excludeFieldsWithoutExposeAnnotation()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .create();

    private WebSocketMessageConverter() {
    }

    public static String convertPollToJson(PollDto poll, String messageContext) {


        JsonObject json = new JsonObject();
        json.addProperty("context", messageContext);
        json.addProperty("type", "poll");
        json.addProperty("pollId", poll.getPollId());
        json.addProperty("pollName", poll.getPollName());
        json.addProperty("optionOne", poll.getOptionOne());
        json.addProperty("optionTwo", poll.getOptionTwo());
        json.addProperty("optionOneVotes", poll.getOptionOneVotes());
        json.addProperty("optionTwoVotes", poll.getOptionTwoVotes());
        json.addProperty("endTime", poll.getEndTime().toString());


        return gson.toJson(json);
    }


    public static String convertVoteToJson(String pollId, VoteDto vote, String messageContext) {


        JsonObject json = new JsonObject();
        json.addProperty("context", messageContext);
        json.addProperty("type", "vote");
        json.addProperty("pollId", pollId);
        json.addProperty("optionOneVotes", vote.getOption1Count());
        json.addProperty("optionTwoVotes", vote.getOption2Count());


        return gson.toJson(json);
    }


}
