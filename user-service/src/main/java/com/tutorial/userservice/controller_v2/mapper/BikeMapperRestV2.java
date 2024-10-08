package com.tutorial.userservice.controller_v2.mapper;

import org.mapstruct.Mapper;

import com.tutorial.userservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.userservice.service.dto.BikeDTO;

@Mapper(componentModel = "spring")
public interface BikeMapperRestV2 {

    BikeRestDtoV2 bikeDTO_2_BikeRestDtoV2(BikeDTO bikeDTO);
}
