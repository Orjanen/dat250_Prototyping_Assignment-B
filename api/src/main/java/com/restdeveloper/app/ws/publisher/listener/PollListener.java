package com.restdeveloper.app.ws.publisher.listener;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.publisher.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class PollListener {
    @Autowired
    private Runner runner;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void pollListener(PollEntity poll) {
        System.out.println("     - PollListener started");
        sendRunner(poll);
    }






    public void sendRunner(PollEntity poll) {
        try {
            runner.run("SENDRUNNER gets poll: " + poll.getId());
        } catch (Exception e) {
            System.out.println("Exception in sendRunner: " + e + "\n\n\n");
            e.printStackTrace();
        }
    }


}
