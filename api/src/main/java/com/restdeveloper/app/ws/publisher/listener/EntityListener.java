package com.restdeveloper.app.ws.publisher.listener;

import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.publisher.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class VoteListener {
    @Autowired
    private Runner runner;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void voteListener(Object vote) {
        System.out.print("\n     - VoteListener started");
        try {
            runner.runObject(vote);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
