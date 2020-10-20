package com.restdeveloper.app.ws.publisher;

import java.util.concurrent.CountDownLatch;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Object message) {
        System.out.print("Received message:\n\t");
        System.out.println(message);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}