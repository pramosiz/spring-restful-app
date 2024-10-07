package com.tutorial.bikeservice.serviceimpl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tutorial.bikeservice.repository.entities.Bike;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

@Mapper(componentModel = "spring")
public interface BikeMapper {

    BikeDTO bike_2_BikeDTO(Bike bike);

    @Mapping(target = "id", ignore = true)
    Bike newBikeDto_2_Bike(NewBikeDTO newBikeDTO);
}
