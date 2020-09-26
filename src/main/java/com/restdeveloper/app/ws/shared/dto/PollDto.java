package com.restdeveloper.app.ws.shared.dto;

public class PollDto {
    private long id;
    private String pollId;
    private String pollName;
    private UserDto userDetails;
    private VoteDto voteEntity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public VoteDto getVoteEntity() {
        return voteEntity;
    }

    public void setVoteEntity(VoteDto voteEntity) {
        this.voteEntity = voteEntity;
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

    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }

    public VoteDto getVoteDetails() {
        return voteEntity;
    }

    public void setVoteDetails(VoteDto voteDetails) {
        this.voteEntity = voteDetails;
    }
}
