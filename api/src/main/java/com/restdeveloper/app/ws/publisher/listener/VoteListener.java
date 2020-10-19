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
    public void voteListener(VoteEntity vote) {
        System.out.println("     - VoteListener started");
        sendRunner(vote);
    }






    public void sendRunner(VoteEntity vote) {
        try {
            runner.run("SENDRUNNER gets poll: " + vote.getId());
        } catch (Exception e) {
            System.out.println("Exception in sendRunner: " + e + "\n\n\n");
            e.printStackTrace();
        }
    }


}
