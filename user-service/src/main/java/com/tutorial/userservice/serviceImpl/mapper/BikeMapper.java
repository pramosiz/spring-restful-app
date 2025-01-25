package com.tutorial.userservice.serviceimpl.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.feignclient.dto.BikeFeignRestDtoV2;
import com.tutorial.userservice.service.dto.BikeDTO;

@Service
public class BikeMapper {

    public BikeDTO bikeFeignRestDtoV2_2_BikeDTO(BikeFeignRestDtoV2 bikeFeignRestDtoV2) {
        if (bikeFeignRestDtoV2 == null) {
            return null;
        } else {
            return BikeDTO.builder()
                    .id(bikeFeignRestDtoV2.getId())
                    .brand(bikeFeignRestDtoV2.getBrand())
                    .model(bikeFeignRestDtoV2.getModel())
                    .userId(bikeFeignRestDtoV2.getUserId())
                    .build();
        }
    }
}
