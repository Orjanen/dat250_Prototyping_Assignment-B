package com.restdeveloper.app.ws.ui.model.response;

import java.util.List;

public class PollRest {
    private String pollId;
    private String pollName;
    private String optionOne;
    private String optionTwo;
    private List<VoteRest> votes;
    private boolean isPrivate;

    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

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

    public List<VoteRest> getVotes() {
        return votes;
    }

    public void setVotes(List<VoteRest> votes) {
        this.votes = votes;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }


}
