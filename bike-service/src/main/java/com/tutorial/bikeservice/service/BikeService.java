package com.tutorial.bikeservice.service;

import java.util.List;
import java.util.Optional;

import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

public interface BikeService {

    List<BikeDTO> getAll();

    Optional<BikeDTO> getById(Long id);

    List<BikeDTO> getByUserId(Long id);

    Optional<BikeDTO> saveNewBikeWithExternalCheck(NewBikeDTO newBikeDTO);

    void deleteById(Long id);

    void deleteByUserId(Long id);
}
