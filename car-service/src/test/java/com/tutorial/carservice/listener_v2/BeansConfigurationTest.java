package com.tutorial.carservice.listener_v2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BeansConfigurationTest {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
