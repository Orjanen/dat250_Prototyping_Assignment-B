package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class Runner {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange fanout;

    public void run(String s) {
        System.out.println("Runner initialized. Sending message...");
        template.convertAndSend(fanout.getName(), "", s);
        System.out.println("Runner done");
    }

}
