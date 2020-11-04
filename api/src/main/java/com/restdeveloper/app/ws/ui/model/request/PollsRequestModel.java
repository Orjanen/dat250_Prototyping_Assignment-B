package com.restdeveloper.app.ws.ui.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;


public class PollsRequestModel {


    @NotNull(message = "Poll must have a question!")
    @Size(min=3, message = "Poll question must be more than 3 characters!")
    private String pollName;

    @NotNull(message = "Poll must have two options to vote for!")
    @NotBlank(message = "Vote options cannot be blank!")
    private String optionOne;
    @NotNull(message = "Poll must have two options to vote for!")
    @NotBlank(message = "Vote options cannot be blank!")
    private String optionTwo;

    @NotNull(message = "Poll must have a duration!")
    private Duration duration;

    private boolean isPrivate;


    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public void setOptionOne(String optionOne) {
        this.optionOne = optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public void setOptionTwo(String optionTwo) {
        this.optionTwo = optionTwo;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
