package com.tutorial.carservice.application.serviceimpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tutorial.carservice.domain.V2.mapper.CarMapperV2;

@Configuration
public class MapperConfig {

    @Bean
    public CarMapperV2 carMapperV2() {
        return new CarMapperV2();
    }
}
