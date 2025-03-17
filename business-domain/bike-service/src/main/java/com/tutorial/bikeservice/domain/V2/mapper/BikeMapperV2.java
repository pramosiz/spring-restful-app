package com.tutorial.bikeservice.domain.V2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;
import com.tutorial.bikeservice.domain.V2.model.BikeV2;

@Service
public class BikeMapperV2 {

    public BikeDtoV2 bikeV2_2_BikeDtoV2(BikeV2 bike) {
        if (bike == null) {
            return null;
        } else {
            return BikeDtoV2.builder()
                    .id(bike.getId())
                    .brand(bike.getBrand())
                    .model(bike.getModel())
                    .userId(bike.getUserId())
                    .build();
        }
    }

    public BikeV2 newBikeDtoV2_2_BikeV2(NewBikeDtoV2 newBikeDTO) {
        if (newBikeDTO == null) {
            return null;
        } else {
            return BikeV2.builder()
                    .brand(newBikeDTO.getBrand())
                    .model(newBikeDTO.getModel())
                    .userId(newBikeDTO.getUserId())
                    .build();
        }
    }
}
