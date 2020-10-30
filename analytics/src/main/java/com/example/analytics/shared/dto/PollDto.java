package com.example.analytics.shared.dto;


public class PollDto {
    private String id;
    private String jpaId;
    private String pollName;
    private String optionOne;
    private String optionTwo;

    private int optionOneVotes;
    private int optionTwoVotes;

    private boolean isStillActive;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJpaId() {
        return jpaId;
    }

    public void setJpaId(String jpaId) {
        this.jpaId = jpaId;
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

    public int getOptionOneVotes() {
        return optionOneVotes;
    }

    public void setOptionOneVotes(int optionOneVotes) {
        this.optionOneVotes = optionOneVotes;
    }

    public int getOptionTwoVotes() {
        return optionTwoVotes;
    }

    public void setOptionTwoVotes(int optionTwoVotes) {
        this.optionTwoVotes = optionTwoVotes;
    }

    public boolean isStillActive() {
        return isStillActive;
    }

    public void setStillActive(boolean stillActive) {
        isStillActive = stillActive;
    }

    @Override
    public String toString() {
        return "PollDto{" +
               "\tid='" + id + '\'' +
               ",\tjpaId='" + jpaId + '\'' +
               ",\tpollName='" + pollName + '\'' +
               ",\toptionOne='" + optionOne + '\'' +
               ",\toptionTwo='" + optionTwo + '\'' +
               ",\toptionOneVotes=" + optionOneVotes +
               ",\toptionTwoVotes=" + optionTwoVotes +
               ",\tisStillActive=" + isStillActive +
               "\t}";
    }
}
