package com.example.analytics.mongodb.io.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PollEntity {

    @Id
    private String id;

    private String jpaId;
    private String pollName;
    private String optionOne;
    private String optionTwo;
    private int optionOneVotes;
    private int optionTwoVotes;

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

    @Override
    public String toString() {
        return String.format(
                "Poll[id=%s, jpaId='%s', pollName='%s', optionOne='%s', optionTwo='%s', " +
                "optionOneVotes=%s, optionTwoVotes=%s]",
                id, jpaId, pollName, optionOne, optionTwo, optionOneVotes, optionTwoVotes);
    }

}
