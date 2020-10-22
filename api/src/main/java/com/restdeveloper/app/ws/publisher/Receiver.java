package com.restdeveloper.app.ws.publisher;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;


@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(Object message) {
        System.out.println("Receiver got message:");
        System.out.println(message);
        latch.countDown();
    }



    public CountDownLatch getLatch() {
        return latch;
    }

}