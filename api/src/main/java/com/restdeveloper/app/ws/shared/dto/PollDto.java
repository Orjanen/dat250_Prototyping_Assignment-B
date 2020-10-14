package com.restdeveloper.app.ws.shared.dto;

import java.util.List;

public class PollDto {
    private long id;
    private String pollId;
    private String pollName;
    private UserDto creator;
    private List<VoteDto> votes;
    private String optionOne;
    private String optionTwo;
    private boolean isPrivate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }


    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public List<VoteDto> getVotes() {
        return votes;
    }
    public void setVotes(List<VoteDto> votes) {
        this.votes = votes;
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
}
