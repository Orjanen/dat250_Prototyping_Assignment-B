package com.restdeveloper.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "polls")
public class PollEntity implements Serializable {
    private static final long serialVersionUID = 829864162685009192L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 30)
    private String pollId;

    @Column(nullable = false, length = 20)
    private String pollName;

    @ManyToOne
    private UserEntity creator; //bruker som opprettet poll

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pollEntity")
    private List<VoteEntity> votes;

    private String optionOne;
    private String optionTwo;

    @Transient
    private int optionOneVotes;
    public int getOptionOneVotes(){
        int numberOfVotes = 0;

        if(votes != null && votes.size() > 0){
            for(VoteEntity v : votes){
                numberOfVotes += v.getOption1Count();
            }
        }

        return numberOfVotes;
    }

    @Transient
    private int optionTwoVotes;
    public int getOptionTwoVotes(){
        int numberOfVotes = 0;
        if(votes != null && votes.size() > 0){
            for(VoteEntity v : votes){
                numberOfVotes += v.getOption2Count();
            }
        }

        return numberOfVotes;
    }



    private boolean isPrivate;


    //TODO: Når ble poll opprettet, hvor lenge skal den vare
    //TODO: liste med IoTDevice



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public UserEntity getCreator() {
        return creator;
    }

    public void setCreator(UserEntity creator) {
        this.creator = creator;
    }

    public List<VoteEntity> getVotes(){
        return votes;
    }

    public void setVotes(List<VoteEntity> votes){
        this.votes = votes;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }
}
