package com.tutorial.bikeservice.controller_v2.mapper;

import org.mapstruct.Mapper;

import com.tutorial.bikeservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.dto.NewBikeRestDtoV2;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

@Mapper(componentModel = "spring")
public interface BikeMapperRestV2 {

    BikeRestDtoV2 bikeDTO_2_BikeRestDtoV2(BikeDTO bikeDTO);

    NewBikeDTO newBikeRestDtoV2_2_NewBikeDTO(NewBikeRestDtoV2 newBikeRestDtoV2);
}
