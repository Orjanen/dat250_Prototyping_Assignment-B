package com.restdeveloper.app.ws.io.entity;

import com.google.gson.annotations.Expose;
import com.restdeveloper.app.ws.publisher.listener.EntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "vote")
@EntityListeners(EntityListener.class)
public class VoteEntity implements Serializable {

    private static final long serialVersionUID= -1061455199903170385L;

    @Id
    @GeneratedValue
    @Expose
    private Long id;
    @Expose
    private String voteId;

    @Expose
    private int option1Count = 0;
    @Expose
    private int option2Count = 0;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    @Expose(serialize = false, deserialize = false)
    private PollEntity pollEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public int getOption1Count() {
        return option1Count;
    }

    public void setOption1Count(int option1Count) {
        this.option1Count = option1Count;
    }

    public int getOption2Count() {
        return option2Count;
    }

    public void setOption2Count(int option2Count) {
        this.option2Count = option2Count;
    }

    public PollEntity getPollEntity() {
        return pollEntity;
    }

    public void setPollEntity(PollEntity pollEntity) {
        this.pollEntity = pollEntity;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
