package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue.name}")
    public void receive(String message) throws InterruptedException {
        System.out.println("Api-Receiver got message:");
        System.out.println(message);
        doWork(message);
    }

    private void doWork(String message) throws InterruptedException {
        for (char ch : message.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
