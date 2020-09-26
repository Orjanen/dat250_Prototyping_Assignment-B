package com.restdeveloper.app.ws.io.entity;

import javax.persistence.*;
import java.io.Serializable;

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
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "pollEntity")
    private VoteEntity voteEntity;

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

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

    public VoteEntity getVoteEntity() {
        return voteEntity;
    }

    public void setVoteEntity(VoteEntity voteEntity) {
        this.voteEntity = voteEntity;
    }
}
