package com.tutorial.carservice.listener_v2;

import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.carservice.service.CarService;

@Configuration
@RabbitListenerTest
public class NotifyDeleteListenerHarness {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public NotifyDeleteListener notifyDeleteListener(CarService carService, ObjectMapper objectMapper) {
        return new NotifyDeleteListener(carService, objectMapper);
    }
}
