package com.tutorial.carservice.application.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.carservice.domain.V2.dto.CarDTO;
import com.tutorial.carservice.domain.V2.dto.NewCarDTO;

public interface CarService {

    List<CarDTO> getAll();

    Optional<CarDTO> getById(Long id);

    List<CarDTO> getByUserId(Long id);

    Optional<CarDTO> saveNewCarWithExternalCheck(NewCarDTO newCarDTO);

    void deleteById(Long id);

    void deleteByUserId(Long userId);
}
