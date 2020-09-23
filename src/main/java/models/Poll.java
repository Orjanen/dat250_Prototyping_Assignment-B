package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @javax.persistence.Id
    @GeneratedValue
    private Long Id;
    private String pollName;

    @OneToMany(mappedBy="poll")
    private List<Vote> votes = new ArrayList<Vote>();

    @OneToMany
    private List<IotDevice> devices;

    @ManyToOne
    private User creator;


    private String optionOne;
    private String optionTwo;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private boolean isPrivate;


    public int numberOfRedVotes(){
        int redVotes = 0;
        for(Vote v : votes){
            redVotes += v.getRed();
        }
        return redVotes;
    }

    public int numberOfGreenVotes(){
        int greenVotes = 0;
        for(Vote v : votes){
            greenVotes += v.getGreen();
        }
        return greenVotes;
    }


    public void addVote(Vote vote) {
        vote.setPoll(this);
        votes.add(vote);
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getPollName() {
        return pollName;
    }

    public void setPollName(String pollName) {
        this.pollName = pollName;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
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

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "Id=" + Id +
                ", pollName='" + pollName + '\'' +
                ", votes=" + votes +
                ", devices=" + devices +
                ", creator=" + creator +
                ", optionOne='" + optionOne + '\'' +
                ", optionTwo='" + optionTwo + '\'' +
                '}';
    }


}
