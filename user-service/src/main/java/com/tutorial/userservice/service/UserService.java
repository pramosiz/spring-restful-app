package com.tutorial.userservice.service;

import java.util.List;
import java.util.Optional;

import com.tutorial.userservice.service.dto.BikeDTO;
import com.tutorial.userservice.service.dto.CarDTO;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

public interface UserService {

    List<UserDTO> getAll();

    Optional<UserDTO> getById(Long id);

    UserDTO saveNewUser(NewUserDTO userDto);

    List<CarDTO> getCarsByUserId(Long userId);

    List<BikeDTO> getBikesByUserId(Long userId);

    void deleteById(Long id);
}
