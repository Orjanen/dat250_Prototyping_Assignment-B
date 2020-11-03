package com.example.analytics.publisher;

import com.example.analytics.handler.JsonHandler;
import com.example.analytics.handler.util.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @Autowired
    JsonHandler jsonHandler;

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = "#{analyticsQueue.name}")
    public void receive(String message) {
        String messageInSmallPrint = message.replace("\n", "");
        LOGGER.info("Analytics-Receiver got message: {}", messageInSmallPrint);

        if (Converter.isValidJson(message)) jsonHandler.initializeHandlingProcess(message);
    }

}
