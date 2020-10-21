package com.restdeveloper.app.ws.publisher;

import com.restdeveloper.app.ws.publisher.util.Converter;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Object message) {
        System.out.print("Received message:\n\t");
        processMessage(message);
        latch.countDown();
    }

    private void processMessage(Object object) {
        String type = object.getClass().getSimpleName();
        System.out.println(type);

        String jsonFormat = Converter.convertToJson(object);


        switch (type) {
            case "VoteEntity":
                System.out.println("VoteEntity:\n" + jsonFormat);
                break;
            case "PollEntity":
                System.out.println("PollEntity:\n" + jsonFormat);
                break;
            case "UserEntity":
                System.out.println("UserEntity:\n" + jsonFormat);
                break;
            default:
                throw new IllegalArgumentException("Message is not an entity");

        }

    }

    public CountDownLatch getLatch() {
        return latch;
    }

}