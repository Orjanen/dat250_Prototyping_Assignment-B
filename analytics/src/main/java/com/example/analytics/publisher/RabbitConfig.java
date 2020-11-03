package com.example.analytics.publisher;

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
    public Queue analyticsQueue() {
        return new Queue("analyticsQueue",true, false, false);
    }

    @Bean
    public Binding analyticsBinding(FanoutExchange fanout, Queue analyticsQueue) {
        return BindingBuilder.bind(analyticsQueue).to(fanout);
    }


}
