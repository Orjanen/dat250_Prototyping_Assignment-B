package com.example.analytics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AnalyticsApplication {//implements CommandLineRunner {


    /*
        @Autowired
        private PollRepository repository;

        @Override
        public void run(String... args) throws Exception {

            repository.deleteAll();

            // save a couple of polls
            repository.save(new PollEntity("p1", "TestPoll", "Yes", "No", 0, 0));
            repository.save(new PollEntity("p2", "TestPoll2", "This", "That", 0, 0));

            // fetch all polls
            System.out.println("Polls found with findAll():");
            System.out.println("-------------------------------");
            for (PollEntity poll : repository.findAll()) {
                System.out.println(poll);
            }
            System.out.println();

            // fetch an individual customer
            System.out.println("Poll found with findByPollName('TestPoll'):");
            System.out.println("--------------------------------");
            System.out.println(repository.findByPollName("TestPoll"));
        }


        public static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange";
        public static final String QUEUE_NAME = "spring-boot";

        @Bean
        Queue queue() {
            return new Queue(QUEUE_NAME, false);
        }

        @Bean
        TopicExchange exchange() {
            return new TopicExchange(TOPIC_EXCHANGE_NAME);
        }

        @Bean
        Binding binding(Queue queue, TopicExchange exchange) {
            return BindingBuilder.bind(queue).to(exchange).with("foo.bar.#");
        }

        @Bean
        SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter listenerAdapter) {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
            container.setConnectionFactory(connectionFactory);
            container.setQueueNames(QUEUE_NAME);
            container.setMessageListener(listenerAdapter);
            return container;
        }

        @Bean
        MessageListenerAdapter listenerAdapter(Receiver receiver) {
            return new MessageListenerAdapter(receiver, "receiveMessage");
        }
    */

    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }

}

