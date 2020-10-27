package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Runner {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanout;

    private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

    public void run(String s) {
        LOGGER.info("Runner initialized. Sending message...");
        template.convertAndSend(fanout.getName(), "", s);
        LOGGER.debug("Runner done");
    }

}
