package com.tutorial.bikeservice.application.serviceimpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tutorial.bikeservice.domain.V2.mapper.BikeMapperV2;

@Configuration
public class MapperConfig {

    @Bean
    public BikeMapperV2 bikeMapperV2() {
        return new BikeMapperV2();
    }
}
