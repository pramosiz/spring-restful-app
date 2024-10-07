package com.tutorial.carservice.service;

import java.util.List;
import java.util.Optional;

import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

import reactor.core.publisher.Mono;

public interface CarService {

    List<CarDTO> getAll();

    Optional<CarDTO> getById(Long id);

    Mono<CarDTO> saveNewCarWithExternalCheck(NewCarDTO newCarDTO);
}
