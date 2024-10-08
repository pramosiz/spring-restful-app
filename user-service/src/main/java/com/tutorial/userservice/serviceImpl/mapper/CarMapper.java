package com.tutorial.userservice.serviceimpl.mapper;

import org.mapstruct.Mapper;

import com.tutorial.userservice.feignclient.dto.CarFeignRestDtoV2;
import com.tutorial.userservice.service.dto.CarDTO;

@Mapper(componentModel = "spring")
public interface CarMapper {

    CarDTO carFeignRestDtoV2_2_CarDTO(CarFeignRestDtoV2 carFeignRestDtoV2);
}
