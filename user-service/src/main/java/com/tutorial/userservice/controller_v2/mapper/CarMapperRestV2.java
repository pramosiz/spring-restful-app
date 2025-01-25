package com.tutorial.userservice.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.userservice.service.dto.CarDTO;

@Service
public class CarMapperRestV2 {

    public CarRestDtoV2 carDTO_2_CarRestDtoV2(CarDTO carDto) {
        if (carDto == null) {
            return null;
        } else {
            return CarRestDtoV2.builder()
                    .id(carDto.getId())
                    .brand(carDto.getBrand())
                    .model(carDto.getModel())
                    .userId(carDto.getUserId())
                    .build();
        }
    }
}
