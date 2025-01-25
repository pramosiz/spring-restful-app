package com.tutorial.userservice.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.userservice.service.dto.BikeDTO;

@Service
public class BikeMapperRestV2 {

    public BikeRestDtoV2 bikeDTO_2_BikeRestDtoV2(BikeDTO bikeDTO) {
        if (bikeDTO == null) {
            return null;
        } else {
            return BikeRestDtoV2.builder()
                    .id(bikeDTO.getId())
                    .brand(bikeDTO.getBrand())
                    .model(bikeDTO.getModel())
                    .userId(bikeDTO.getUserId())
                    .build();
        }
    }
}
