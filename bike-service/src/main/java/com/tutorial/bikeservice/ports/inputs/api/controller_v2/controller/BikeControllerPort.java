package com.tutorial.bikeservice.ports.inputs.api.controller_v2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.NewBikeRestDtoV2;

public interface BikeControllerPort {

	ResponseEntity<List<BikeRestDtoV2>> getAll();

	ResponseEntity<BikeRestDtoV2> getById(Long id);

	ResponseEntity<BikeRestDtoV2> saveNewBike(NewBikeRestDtoV2 newBikeRestDtoV2);

	ResponseEntity<Void> deleteById(Long id);

	ResponseEntity<List<BikeRestDtoV2>> getByUserId(Long userId);
}
