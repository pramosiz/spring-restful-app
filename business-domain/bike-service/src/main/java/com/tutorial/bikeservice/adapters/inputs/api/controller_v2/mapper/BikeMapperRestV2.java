package com.tutorial.bikeservice.adapters.inputs.api.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.NewBikeRestDtoV2;

@Service
public class BikeMapperRestV2 {

    public BikeRestDtoV2 bikeDTO_2_BikeRestDtoV2(BikeDtoV2 bikeDTO) {
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

    public NewBikeDtoV2 newBikeRestDtoV2_2_NewBikeDTO(NewBikeRestDtoV2 newBikeRestDtoV2) {
        if (newBikeRestDtoV2 == null) {
            return null;
        } else {
            return NewBikeDtoV2.builder()
                    .brand(newBikeRestDtoV2.getBrand())
                    .model(newBikeRestDtoV2.getModel())
                    .userId(newBikeRestDtoV2.getUserId())
                    .build();
        }
    }
}
