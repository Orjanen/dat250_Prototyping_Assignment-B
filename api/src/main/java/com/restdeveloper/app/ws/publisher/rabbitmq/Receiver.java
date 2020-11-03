package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "#{apiQueue.name}")
    public void receive(String message) {
        String messageInSmallPrint = message.replace("\n", "");
        LOGGER.debug("Api-Receiver got message: {}", messageInSmallPrint);
    }

}
