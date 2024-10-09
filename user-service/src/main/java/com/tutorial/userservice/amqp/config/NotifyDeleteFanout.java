package com.tutorial.userservice.amqp.config;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotifyDeleteFanout {

    private static final String NOTIFY_DELETE_INFO = "NOTIFY_DELETE_INFO";

    @Bean
    public FanoutExchange notifyDeleteInfoFanout() {
        return new FanoutExchange(NOTIFY_DELETE_INFO, true, false);
    }

}
