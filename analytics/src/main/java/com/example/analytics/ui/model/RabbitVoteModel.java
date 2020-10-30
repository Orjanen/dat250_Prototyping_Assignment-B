package com.example.analytics.ui.model;

public class RabbitVoteModel {


    private String type;
    private String jpaId;
    private int optionOneVotes;
    private int optionTwoVotes;


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

    @Override
    public String toString() {
        return "RabbitVoteModel{" +
               "\ttype='" + type + '\'' +
               ",\tjpaId='" + jpaId + '\'' +
               ",\toptionOneVotes=" + optionOneVotes +
               ",\toptionTwoVotes=" + optionTwoVotes +
               "\t}";
    }
}
