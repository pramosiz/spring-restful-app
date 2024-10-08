package com.tutorial.userservice.controller_v2.mapper;

import org.mapstruct.Mapper;

import com.tutorial.userservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.userservice.service.dto.CarDTO;

@Mapper(componentModel = "spring")
public interface CarMapperRestV2 {

    CarRestDtoV2 carDTO_2_CarRestDtoV2(CarDTO carDto);
}
