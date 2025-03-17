package com.tutorial.userservice.serviceimpl.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.feignclient.dto.CarFeignRestDtoV2;
import com.tutorial.userservice.service.dto.CarDTO;

@Service
public class CarMapper {

    public CarDTO carFeignRestDtoV2_2_CarDTO(CarFeignRestDtoV2 carFeignRestDtoV2) {
        if (carFeignRestDtoV2 == null) {
            return null;
        } else {
            return CarDTO.builder()
                    .id(carFeignRestDtoV2.getId())
                    .brand(carFeignRestDtoV2.getBrand())
                    .model(carFeignRestDtoV2.getModel())
                    .userId(carFeignRestDtoV2.getUserId())
                    .build();
        }
    }
}
