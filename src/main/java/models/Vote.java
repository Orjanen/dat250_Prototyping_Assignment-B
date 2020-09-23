package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Vote {

    @javax.persistence.Id
    @GeneratedValue
    private Long Id;
    private int red;
    private int green;

    @ManyToOne
    @JoinColumn(name="poll_id")
    private Poll poll;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User voteCastByUser;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public User getVoteCastByUser() {
        return voteCastByUser;
    }

    public void setVoteCastByUser(User voteCastByUser) {
        this.voteCastByUser = voteCastByUser;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "Id=" + Id +
                ", red=" + red +
                ", green=" + green +
                ", poll=" + poll.getPollName() +
                ", voteCastByUser=" + voteCastByUser.getId() +
                '}';
    }
}
