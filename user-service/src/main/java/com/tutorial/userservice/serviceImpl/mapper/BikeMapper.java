package com.tutorial.userservice.serviceimpl.mapper;

import org.mapstruct.Mapper;

import com.tutorial.userservice.feignclient.dto.BikeFeignRestDtoV2;
import com.tutorial.userservice.service.dto.BikeDTO;

@Mapper(componentModel = "spring")
public interface BikeMapper {

    BikeDTO bikeFeignRestDtoV2_2_BikeDTO(BikeFeignRestDtoV2 bikeFeignRestDtoV2);
}
