package com.tutorial.carservice.serviceimpl.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.repository.domains.Car;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

@Service
public class CarMapper {

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
