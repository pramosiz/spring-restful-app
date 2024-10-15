package com.tutorial.carservice.amqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// @ConfigurationProperties -> TODO: Estudiar esto
public class NotifyDeleteFanout {

    private static final String NOTIFY_DELETE_INFO_EXCHANGE = "NOTIFY_DELETE_INFO";
    private static final String CAR_SUSCRIBER_QUEUE = "car_suscriber";

    @Bean
    public FanoutExchange notifyDeleteInfoFanout() {
        return new FanoutExchange(NOTIFY_DELETE_INFO_EXCHANGE, true, false);
    }

    @Bean
    public Queue carSuscriberQueue() {
        return new Queue(CAR_SUSCRIBER_QUEUE, true, false, false);
    }

    @Bean
    public Binding notifyDeleteInfoBinding(Queue carSuscriberQueue, FanoutExchange notifyDeleteInfoFanout) {
        return BindingBuilder.bind(carSuscriberQueue).to(notifyDeleteInfoFanout);
    }
}
