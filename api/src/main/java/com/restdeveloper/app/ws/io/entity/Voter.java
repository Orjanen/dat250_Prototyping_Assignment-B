package com.restdeveloper.app.ws.io.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Voter {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "voter")
    private List<VoteEntity> myVotes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<VoteEntity> getMyVotes() {
        return myVotes;
    }

    public void setMyVotes(List<VoteEntity> myVotes) {
        this.myVotes = myVotes;
    }
}
