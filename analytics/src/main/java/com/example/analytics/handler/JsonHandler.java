package com.example.analytics.handler;

import com.example.analytics.handler.util.Converter;
import com.example.analytics.service.PollService;
import com.example.analytics.shared.dto.PollDto;
import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JsonHandler {

    @Autowired
    private PollService pollService;

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonHandler.class);

    ModelMapper modelMapper = new ModelMapper();


    public void initializeHandlingProcess(String message) {
        JsonObject jsonObject = Converter.convertToJsonObject(message);

        try {
            String type = getTypeFromMessage(jsonObject);
            switch (type) {
                case "poll":
                    handlePoll(message);
                    break;
                case "vote":
                    handleVote(message);
                    break;
                default:
                    LOGGER.warn("JsonHandler has no implementation with entity-type: {}", type);
                    break;
            }
        } catch (NoSuchFieldException e) {
            LOGGER.error("JsonHandler got error from getTypeFromMessage: ", e);
        }

    }

    private String getTypeFromMessage(JsonObject jsonObject) throws NoSuchFieldException {
        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
            LOGGER.trace("Iterating over jsonObject gives element: {}", e);
            String elementKey = e.getKey().toLowerCase();
            if (elementKey.equals("type")) return e.getValue().getAsString().toLowerCase();
        }
        throw new NoSuchFieldException("Could not find element-key 'type'");
    }

    private void handleVote(String message) {
        LOGGER.debug("Started handling of received vote");
        RabbitVoteModel voteModel = Converter.convertToVoteModel(message);

        String jpaId = voteModel.getJpaId();
        PollDto receivedPoll = modelMapper.map(voteModel, PollDto.class);
        pollService.updatePollVotes(receivedPoll, jpaId);

        LOGGER.debug("Vote-handling done");

    }

    private void handlePoll(String message) {
        LOGGER.debug("Started handling of received poll");

        RabbitPollModel pollModel = Converter.convertToPollModel(message);
        PollDto receivedPoll = modelMapper.map(pollModel, PollDto.class);
        pollService.updatePoll(receivedPoll);

        LOGGER.debug("Poll-handling done");
    }

}
