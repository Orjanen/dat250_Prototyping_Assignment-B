package com.example.analytics.ui.model;

public class RabbitPollModel {

    private String type;
    private String jpaId;
    private String pollName;
    private String optionOne;
    private String optionTwo;

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

    @Override
    public String toString() {
        return "RabbitPollModel{" +
               "\n\ttype='" + type + '\'' +
               "\n\tjpaId='" + jpaId + '\'' +
               "\n\tpollName='" + pollName + '\'' +
               "\n\toptionOne='" + optionOne + '\'' +
               "\n\toptionTwo='" + optionTwo + '\'' +
               "\n}\n";
    }
}
