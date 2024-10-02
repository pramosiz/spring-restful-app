package com.tutorial.carservice.controller_v2.mapper;

import org.mapstruct.Mapper;

import com.tutorial.carservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.carservice.controller_v2.dto.NewCarRestDtoV2;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

@Mapper(componentModel = "spring")
public interface CarMapperRestV2 {

    CarRestDtoV2 carDTO_2_CarRestDtoV2(CarDTO carDTO);

    NewCarDTO newCarRestDtoV2_2_NewCarDTO(NewCarRestDtoV2 newCarRestDtoV2);
}
