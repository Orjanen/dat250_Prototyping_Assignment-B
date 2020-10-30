package com.restdeveloper.app.ws.publisher.listener;

import com.restdeveloper.app.ws.io.entity.PollEntity;
import com.restdeveloper.app.ws.io.entity.VoteEntity;
import com.restdeveloper.app.ws.publisher.rabbitmq.Runner;
import com.restdeveloper.app.ws.publisher.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import java.io.InvalidObjectException;

@Component
public class EntityListener {

    @Autowired
    private Runner runner;

    private static final Logger LOGGER = LoggerFactory.getLogger(EntityListener.class);

    @PostPersist
    @PostUpdate
    @PostRemove
    public void entityListener(Object entity) {
        LOGGER.info("EntityListener initialized with entity: {}", entity.getClass().getSimpleName());

        try {
            String json = processEntity(entity);
            runner.run(json);
        } catch (InvalidObjectException e) {
            LOGGER.error("EntityListener got error from processEntity: ", e);
        }
    }

    private String processEntity(Object entity) throws InvalidObjectException {
        LOGGER.debug("Processing entity {}...", entity.getClass().getSimpleName());

        String type = entity.getClass().getSimpleName();

        switch (type) {
            case "VoteEntity":
                return Converter.convertVoteToJson((VoteEntity) entity);
            case "PollEntity":
                return Converter.convertPollToJson((PollEntity) entity);
            default:
                throw new InvalidObjectException("EntityListener has no implementation with entity: " + type);
        }
    }

}
