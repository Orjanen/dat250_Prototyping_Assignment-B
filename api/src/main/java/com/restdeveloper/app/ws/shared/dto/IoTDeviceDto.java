package com.restdeveloper.app.ws.shared.dto;

public class IoTDeviceDto {
    private long id;
    private String publicDeviceId;
    private PollDto currentPoll;

    //Not saved to DB
    private VoteDto votesForCurrentPoll;


    public String getPublicDeviceId() {
        return publicDeviceId;
    }

    public void setPublicDeviceId(String publicDeviceId) {
        this.publicDeviceId = publicDeviceId;
    }

    public PollDto getCurrentPoll() {
        return currentPoll;
    }

    public void setCurrentPoll(PollDto currentPoll) {
        this.currentPoll = currentPoll;
    }

    public VoteDto getVotesForCurrentPoll() {
        return votesForCurrentPoll;
    }

    public void setVotesForCurrentPoll(VoteDto votesForCurrentPoll) {
        this.votesForCurrentPoll = votesForCurrentPoll;
    }
}
