package com.tutorial.carservice.serviceimpl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tutorial.carservice.repository.domains.Car;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO car_2_CarDTO(Car car);

    @Mapping(target = "id", ignore = true)
    Car newCarDto_2_Car(NewCarDTO newCarDTO);
}
