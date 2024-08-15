package com.chervonnaya.subscriptionsservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "exchange";
    public static final String PUBLICATIONS_QUEUE = "pubQueue";
    public static final String PUBLICATIONS_KEY = "pubKey";
    public static final String COMMENTS_QUEUE = "comQueue";
    public static final String COMMENTS_KEY = "comKey";
    public static final String LIKES_QUEUE = "likeQueue";
    public static final String LIKES_KEY = "likeKey";
    public static final String NOTIFICATIONS_KEY = "notKey";

    @Bean
    public Queue pubsQueue() {
        return new Queue(PUBLICATIONS_QUEUE, false);
    }

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
    public Binding pubsBinding(Queue pubsQueue, DirectExchange exchange) {
        return BindingBuilder.bind(pubsQueue).to(exchange).with(PUBLICATIONS_KEY);
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
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
