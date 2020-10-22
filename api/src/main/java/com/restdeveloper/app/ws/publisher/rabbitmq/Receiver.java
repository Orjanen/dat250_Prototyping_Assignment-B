package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String message) {
        System.out.println("Api-Receiver got message:");
        System.out.println(message);
    }

}
