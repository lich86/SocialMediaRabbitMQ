package com.chervonnaya.activityservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange";
    public static final String COMMENTS_QUEUE = "comQueue";
    public static final String COMMENTS_KEY = "comKey";
    public static final String LIKES_QUEUE = "likeQueue";
    public static final String LIKES_KEY = "likeKey";

    @Bean
    public Queue commsQueue() {
        return new Queue(COMMENTS_QUEUE, false);
    }

    @Bean
    public Queue likesQueue() {
        return new Queue(LIKES_QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }


    @Bean
    public Binding commsBinding(Queue commsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(commsQueue).to(exchange).with(COMMENTS_KEY);
    }

    @Bean
    public Binding likesBinding(Queue likesQueue, DirectExchange exchange) {
        return BindingBuilder.bind(likesQueue).to(exchange).with(LIKES_KEY);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setMissingQueuesFatal(false);
        return factory;
    }
}
