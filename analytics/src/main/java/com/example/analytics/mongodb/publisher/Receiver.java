package com.example.analytics.mongodb.publisher;

import com.example.analytics.mongodb.handler.JsonHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    JsonHandler jsonHandler;

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String message) throws InterruptedException {
        System.out.println("Analytics-Receiver got message:");
        System.out.println(message);

        //jsonHandler.initializeHandlingProcess(message);
    }

}
