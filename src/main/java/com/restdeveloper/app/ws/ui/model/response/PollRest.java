package com.restdeveloper.app.ws.ui.model.response;

public class PollRest {
    private String pollId;
    private String pollName;

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
}
