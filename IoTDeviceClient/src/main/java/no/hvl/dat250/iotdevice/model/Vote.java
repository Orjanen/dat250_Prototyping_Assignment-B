package no.hvl.dat250.iotdevice.model;

public class Vote {
    private String pollId;
    private Poll poll;
    private int optionOneVotes;
    private int optionTwoVotes;

    public Vote(String pollId, int optionOneVotes, int optionTwoVotes){
        this.pollId = pollId;
        this.optionOneVotes = optionOneVotes;
        this.optionTwoVotes = optionTwoVotes;
    }

    public Vote(String pollId){
        this.pollId = pollId;
        optionOneVotes = 0;
        optionTwoVotes = 0;
    }


    public int voteForOptionOne(){
        this.optionOneVotes += 1;
        return this.optionOneVotes;
    }

    public int voteForOptionTwo() {
        this.optionTwoVotes += 1;
        return this.optionTwoVotes;
    }



    public String getPollId() {
        return pollId;
    }

    public void setPollId(String pollId) {
        this.pollId = pollId;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
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
        return "Vote{" +
                "pollId='" + pollId + '\'' +
                ", poll=" + poll +
                ", optionOneVotes=" + optionOneVotes +
                ", optionTwoVotes=" + optionTwoVotes +
                '}';
    }
}
