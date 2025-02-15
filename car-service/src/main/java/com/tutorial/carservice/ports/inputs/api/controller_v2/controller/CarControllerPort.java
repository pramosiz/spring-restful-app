package com.tutorial.carservice.ports.inputs.api.controller_v2.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.tutorial.carservice.ports.inputs.api.controller_v2.dto.CarRestDtoV2;
import com.tutorial.carservice.ports.inputs.api.controller_v2.dto.NewCarRestDtoV2;

public interface CarControllerPort {

	ResponseEntity<List<CarRestDtoV2>> getAll();

	ResponseEntity<CarRestDtoV2> getById(Long id);

	ResponseEntity<CarRestDtoV2> saveNewBike(NewCarRestDtoV2 newCarRestDtoV2);

	ResponseEntity<Void> deleteById(Long id);

	ResponseEntity<List<CarRestDtoV2>> getByUserId(Long userId);
}
