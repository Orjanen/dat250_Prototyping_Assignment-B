package com.restdeveloper.app.ws.shared.dto;

public class VoteDto {
    private long id;
    private String voteId;
    private int option1Count;
    private int option2Count;

    private String pollId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public int getOption1Count() {
        return option1Count;
    }

    public void setOption1Count(int option1Count) {
        this.option1Count = option1Count;
    }

    public int getOption2Count() {
        return option2Count;
    }

    public void setOption2Count(int option2Count) {
        this.option2Count = option2Count;
    }


    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }
}
