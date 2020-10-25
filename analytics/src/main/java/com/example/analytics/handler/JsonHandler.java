package com.example.analytics.handler;

import com.example.analytics.handler.util.Converter;
import com.example.analytics.service.PollService;
import com.example.analytics.shared.dto.PollDto;
import com.example.analytics.ui.model.RabbitPollModel;
import com.example.analytics.ui.model.RabbitVoteModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class JsonHandler {

    @Autowired
    private PollService pollService;

    ModelMapper modelMapper = new ModelMapper();


    public void initializeHandlingProcess(String message) {
        JsonObject jsonObject = Converter.convertToJsonObject(message);

        for (Map.Entry<String, JsonElement> e : jsonObject.entrySet()) {
            String type = e.getValue().getAsString();
            switch (type) {
                case "poll":
                    handlePoll(message);
                    break;
                case "vote":
                    handleVote(message);
                    break;
                default:
                    System.out.println("JsonHandler has no implementation with entity: " + type);
                    break;
            }
            break;
        }
    }

    private void handleVote(String message) {
        System.out.println("Initialized vote-handling...");
        RabbitVoteModel voteModel = Converter.convertToVoteModel(message);

        String jpaId = voteModel.getJpaId();
        PollDto receivedPoll = modelMapper.map(voteModel, PollDto.class);
        pollService.updatePollVotes(receivedPoll, jpaId);

        System.out.println("Vote-handling done");

    }

    private void handlePoll(String message) {
        System.out.println("Initialized poll-handling...");

        RabbitPollModel pollModel = Converter.convertToPollModel(message);
        PollDto receivedPoll = modelMapper.map(pollModel, PollDto.class);
        pollService.createPoll(receivedPoll);

        System.out.println("Poll-handling done");
    }

}
