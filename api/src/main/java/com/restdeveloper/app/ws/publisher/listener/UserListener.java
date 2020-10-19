package com.restdeveloper.app.ws.publisher.listener;

import com.restdeveloper.app.ws.io.entity.UserEntity;
import com.restdeveloper.app.ws.publisher.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class UserListener {
    @Autowired
    private Runner runner;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void userListener(UserEntity user) {
        System.out.println("     - UserListener started");
        sendRunner(user);
    }






    public void sendRunner(UserEntity user) {
        try {
            runner.run("SENDRUNNER gets user: " + user.getId());
        } catch (Exception e) {
            System.out.println("Exception in sendRunner: " + e + "\n\n\n");
            e.printStackTrace();
        }
    }


}
