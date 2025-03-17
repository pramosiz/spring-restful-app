package com.tutorial.bikeservice.application.services;

import java.util.List;
import java.util.Optional;

import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;

public interface BikeService {

    List<BikeDtoV2> getAll();

    Optional<BikeDtoV2> getById(Long id);

    List<BikeDtoV2> getByUserId(Long id);

    Optional<BikeDtoV2> saveNewBikeWithExternalCheck(NewBikeDtoV2 newBikeDTO);

    void deleteById(Long id);

    void deleteByUserId(Long id);
}
