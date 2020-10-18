package com.restdeveloper.app.ws.publisher;

import java.util.concurrent.TimeUnit;

import com.restdeveloper.app.ws.SpringAppWsApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");

        Object message = "Hello from RabbitMQ!";

        rabbitTemplate.convertAndSend(SpringAppWsApplication.topicExchangeName,
                                      "foo.bar.baz",
                                      message);
        System.out.println(receiver.getLatch().await(10000, TimeUnit.MILLISECONDS));
    }

}