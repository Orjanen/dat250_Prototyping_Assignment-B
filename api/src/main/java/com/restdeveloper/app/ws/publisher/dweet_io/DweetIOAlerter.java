package com.restdeveloper.app.ws.publisher.dweet_io;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class DweetIOAlerter {

    private static final Logger LOGGER = LoggerFactory.getLogger(DweetIOAlerter.class);

    static final String BASE_POST_DWEET_URL = "http://dweet.io/dweet/for/DAT250-2020H-G2-";
    static final String BASE_GET_DWEET_URL = "http://dweet.io/get/dweets/for/DAT250-2020H-G2-";
    static final String BASE_DWEET_FOLLOW_URL = "http://dweet.io/follow/DAT250-2020H-G2-";

    public void notifyDweetAboutPoll(PollEntity poll, String status) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("pollQuestion", poll.getPollName());
        jsonObject.addProperty("optionOne", poll.getOptionOne());
        jsonObject.addProperty("optionTwo", poll.getOptionTwo());
        jsonObject.addProperty("endTime", poll.getEndTime().toString());
        jsonObject.addProperty("startTime", poll.getStartTime().toString());
        jsonObject.addProperty("status", status);
        if (status.equals(DweetStatusConstants.POLL_ENDED)) {
            jsonObject.addProperty("totalVotesForOptionOne", poll.getOptionOneVotes());
            jsonObject.addProperty("totalVotesForOptiontwo", poll.getOptionTwoVotes());
        }

        String fullPollUrl = BASE_POST_DWEET_URL + poll.getPollId();

        LOGGER.info("Posting dweet about poll: {}", poll.getPollId());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        Gson gson = new Gson();


        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(jsonObject), headers);


        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.postForEntity(fullPollUrl, entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.info("Dweet about poll: {} succeeded: {}{} | {}{}",
                        poll.getPollId(),
                        BASE_GET_DWEET_URL, poll.getPollId(),
                        BASE_DWEET_FOLLOW_URL, poll.getPollId());
        } else {
            LOGGER.info("Dweet about poll: {} failed: {}", poll.getPollId(), response.getStatusCode());
        }


    }

}
