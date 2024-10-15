package com.tutorial.bikeservice.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifyDeleteFanout {

    private static final String NOTIFY_DELETE_INFO_EXCHANGE = "NOTIFY_DELETE_INFO";
    private static final String BIKE_SUSCRIBER_QUEUE = "bike_suscriber";

    @Bean
    public FanoutExchange notifyDeleteInfoFanout() {
        return new FanoutExchange(NOTIFY_DELETE_INFO_EXCHANGE, true, false);
    }

    @Bean
    public Queue bikeSuscriberQueue() {
        return new Queue(BIKE_SUSCRIBER_QUEUE, true, false, false);
    }

    @Bean
    public Binding notifyDeleteInfoBinding(Queue bikeSuscriberQueue, FanoutExchange notifyDeleteInfoFanout) {
        return BindingBuilder.bind(bikeSuscriberQueue).to(notifyDeleteInfoFanout);
    }
}
