package com.restdeveloper.app.ws.publisher.listener;

import com.google.gson.JsonObject;
import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.publisher.Runner;
import com.restdeveloper.app.ws.publisher.util.Converter;
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
    public void entityListener(Object entity) {
        System.out.println("EntityListener initialized with entity: " + entity.getClass().getSimpleName());
        String json = processEntity(entity);

        try {
            runner.run(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String processEntity(Object entity) {
        String type = entity.getClass().getSimpleName();

        switch (type) {
            case "VoteEntity":
                return Converter.convertVoteToJson((VoteEntity) entity);
            case "PollEntity":
                return Converter.convertPollToJson((PollEntity) entity);
            default:
                throw new IllegalArgumentException("EntityListener has no implementation with entity: " + type);
        }
    }

}
