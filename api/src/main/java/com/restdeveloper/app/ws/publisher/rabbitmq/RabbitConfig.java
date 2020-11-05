package com.restdeveloper.app.ws.publisher.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("fanout");
    }

    @Bean
    public Queue analyticsQueue() {
        return new Queue("analyticsQueue", true, false, false);
    }

    @Bean
    public Queue apiQueue() {
        return new Queue("apiQueue", true, false, false);
    }

    @Bean
    public Binding analyticsBinding(FanoutExchange fanout, Queue analyticsQueue) {
        return BindingBuilder.bind(analyticsQueue).to(fanout);
    }

    @Bean
    public Binding apiBinding(FanoutExchange fanout, Queue apiQueue) {
        return BindingBuilder.bind(apiQueue).to(fanout);
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