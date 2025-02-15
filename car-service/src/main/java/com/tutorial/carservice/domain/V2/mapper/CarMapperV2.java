package com.tutorial.carservice.domain.V2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.domain.V2.dto.CarDTO;
import com.tutorial.carservice.domain.V2.dto.NewCarDTO;
import com.tutorial.carservice.domain.V2.model.Car;

@Service
public class CarMapperV2 {

    public CarDTO car_2_CarDTO(Car car) {
        if (car == null) {
            return null;
        } else {
            return CarDTO.builder()
                    .id(car.getId())
                    .brand(car.getBrand())
                    .model(car.getModel())
                    .userId(car.getUserId())
                    .build();
        }
    }

    public Car newCarDto_2_Car(NewCarDTO newCarDTO) {
        if (newCarDTO == null) {
            return null;
        } else {
            return Car.builder()
                    .brand(newCarDTO.getBrand())
                    .model(newCarDTO.getModel())
                    .userId(newCarDTO.getUserId())
                    .build();
        }
    }
}
