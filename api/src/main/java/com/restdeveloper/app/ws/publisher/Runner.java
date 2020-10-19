package com.restdeveloper.app.ws.publisher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        // NOTE: This following code-block is only for "debugging"
        List<String> tempDebugArgs = new ArrayList<>(Arrays.asList(args));
        tempDebugArgs.addAll(Arrays.asList("Hello test 1!","Hello test 2!"));
        System.out.println("\n\n\n     TESTMETHOD START:   Length of args: " + tempDebugArgs.size() + "\n");
        for (String s : tempDebugArgs) {System.out.println(s);}
        System.out.println("\n     TESTMETHOD DONE \n\n\n");



        for (String s : tempDebugArgs) {
            System.out.println("Sending message...");


            rabbitTemplate.convertAndSend(SpringAppWsApplication.topicExchangeName,
                                          "foo.bar.baz",
                                          s);
            receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
        }
    }


}