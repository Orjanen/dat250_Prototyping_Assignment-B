package no.hvl.dat250.iotdevice.model;

import java.time.Duration;
import java.util.List;

public class Poll {
    private String pollId;
    private String pollName;
    private String optionOne;
    private String optionTwo;

    private boolean isPrivate;

    private int optionOneVotes;
    private int optionTwoVotes;

    private Duration timeRemaining;


    public Poll(String pollId, String pollName, String optionOne, String optionTwo, int optionOneVotes, int optionTwoVotes, Duration timeRemaining) {
        this.pollId = pollId;
        this.pollName = pollName;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionOneVotes = optionOneVotes;
        this.optionTwoVotes = optionTwoVotes;
        this.timeRemaining = timeRemaining;
    }

    public void addVote(Vote vote){
        this.optionOneVotes += vote.getOptionOneVotes();
        this.optionTwoVotes += vote.getOptionTwoVotes();
    }

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

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
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
        return "Poll{" +
                "pollId='" + pollId + '\'' +
                ", pollName='" + pollName + '\'' +
                ", optionOne='" + optionOne + '\'' +
                ", optionTwo='" + optionTwo + '\'' +
                ", isPrivate=" + isPrivate +
                ", optionOneVotes=" + optionOneVotes +
                ", optionTwoVotes=" + optionTwoVotes +
                ", timeRemaining=" + timeRemaining +
                '}';
    }
}
