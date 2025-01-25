package com.tutorial.carservice.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.carservice.controller_v2.dto.NewCarRestDtoV2;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

@Service
public class CarMapperRestV2 {

    public CarRestDtoV2 carDTO_2_CarRestDtoV2(CarDTO carDTO) {
        if (carDTO == null) {
            return null;
        } else {
            return CarRestDtoV2.builder()
                    .id(carDTO.getId())
                    .brand(carDTO.getBrand())
                    .model(carDTO.getModel())
                    .userId(carDTO.getUserId())
                    .build();
        }
    }

    public NewCarDTO newCarRestDtoV2_2_NewCarDTO(NewCarRestDtoV2 newCarRestDtoV2) {
        if (newCarRestDtoV2 == null) {
            return null;
        } else {
            return NewCarDTO.builder()
                    .brand(newCarRestDtoV2.getBrand())
                    .model(newCarRestDtoV2.getModel())
                    .userId(newCarRestDtoV2.getUserId())
                    .build();
        }
    }
}
