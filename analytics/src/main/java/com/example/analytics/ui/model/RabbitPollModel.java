package com.example.analytics.ui.model;

public class RabbitPollModel {

    private String type;
    private String jpaId;
    private String pollName;
    private String optionOne;
    private String optionTwo;
    private int optionOneVotes;
    private int optionTwoVotes;
    private boolean isStillActive;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        return "RabbitPollModel{" +
               "\ttype='" + type + '\'' +
               ",\tjpaId='" + jpaId + '\'' +
               ",\tpollName='" + pollName + '\'' +
               ",\toptionOne='" + optionOne + '\'' +
               ",\toptionTwo='" + optionTwo + '\'' +
               "\t}";
    }
}
