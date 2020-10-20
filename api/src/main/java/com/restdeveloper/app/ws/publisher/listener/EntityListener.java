package com.restdeveloper.app.ws.publisher.listener;

import com.restdeveloper.app.ws.publisher.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Component
public class EntityListener {
    @Autowired
    private Runner runner;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void voteListener(Object entity) {
        System.out.print("\n\t- EntityListener started with entity: " + entity.getClass().getSimpleName());
        try {
            runner.runObject(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
