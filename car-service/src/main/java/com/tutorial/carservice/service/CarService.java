package com.tutorial.carservice.service;

import java.util.List;
import java.util.Optional;

import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

public interface CarService {

    List<CarDTO> getAll();

    Optional<CarDTO> getById(Long id);

    List<CarDTO> getByUserId(Long id);

    Optional<CarDTO> saveNewCarWithExternalCheck(NewCarDTO newCarDTO);
}
