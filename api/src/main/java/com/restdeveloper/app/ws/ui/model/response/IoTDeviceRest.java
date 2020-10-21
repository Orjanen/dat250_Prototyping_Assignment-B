package com.restdeveloper.app.ws.ui.model.response;

public class IoTDeviceRest {
    private String publicDeviceId;
    private VoteRest votesForCurrentPoll;
    private PollRest currentPoll;


    public String getPublicDeviceId() {
        return publicDeviceId;
    }

    public void setPublicDeviceId(String publicDeviceId) {
        this.publicDeviceId = publicDeviceId;
    }

    public VoteRest getVotesForCurrentPoll() {
        return votesForCurrentPoll;
    }

    public void setVotesForCurrentPoll(VoteRest votesForCurrentPoll) {
        this.votesForCurrentPoll = votesForCurrentPoll;
    }

    public PollRest getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(PollRest currentPoll) {
        this.currentPoll = currentPoll;
    }
}
