package com.tutorial.bikeservice.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.dto.NewBikeRestDtoV2;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

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

    public NewBikeDTO newBikeRestDtoV2_2_NewBikeDTO(NewBikeRestDtoV2 newBikeRestDtoV2) {
        if (newBikeRestDtoV2 == null) {
            return null;
        } else {
            return NewBikeDTO.builder()
                    .brand(newBikeRestDtoV2.getBrand())
                    .model(newBikeRestDtoV2.getModel())
                    .userId(newBikeRestDtoV2.getUserId())
                    .build();
        }
    }
}
