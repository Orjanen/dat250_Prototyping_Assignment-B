package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("fanout");
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange fanout, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public Runner runner() {
        return new Runner();
    }

}