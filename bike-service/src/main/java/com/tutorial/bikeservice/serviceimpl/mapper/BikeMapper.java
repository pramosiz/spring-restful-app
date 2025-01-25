package com.tutorial.bikeservice.serviceimpl.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.bikeservice.repository.domains.Bike;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

@Service
public class BikeMapper {

    public BikeDTO bike_2_BikeDTO(Bike bike) {
        if (bike == null) {
            return null;
        } else {
            return BikeDTO.builder()
                    .id(bike.getId())
                    .brand(bike.getBrand())
                    .model(bike.getModel())
                    .userId(bike.getUserId())
                    .build();
        }
    }

    public Bike newBikeDto_2_Bike(NewBikeDTO newBikeDTO) {
        if (newBikeDTO == null) {
            return null;
        } else {
            return Bike.builder()
                    .brand(newBikeDTO.getBrand())
                    .model(newBikeDTO.getModel())
                    .userId(newBikeDTO.getUserId())
                    .build();
        }
    }
}
