package com.example.analytics.publisher;

import com.example.analytics.handler.JsonHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    JsonHandler jsonHandler;

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String message) throws InterruptedException {
        System.out.println("Analytics-Receiver got message:");
        System.out.println(message);

        jsonHandler.initializeHandlingProcess(message);
    }

}
