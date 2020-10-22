package com.restdeveloper.app.ws.publisher;

import com.restdeveloper.app.ws.SpringAppWsApplication;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

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
            System.out.println("Runner initialized with args-length: " + args.length);

        for (Object s : args) {
            System.out.println("Sending message...");
            rabbitTemplate.convertAndSend(SpringAppWsApplication.topicExchangeName, "foo.bar.baz", s);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }

        System.out.println("Runner done");

    }


}