package com.tutorial.carservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.repository.domains.Car;
import com.tutorial.carservice.repository.repositories.CarRepository;
import com.tutorial.carservice.service.CarService;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;
import com.tutorial.carservice.serviceimpl.mapper.CarMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

	private final CarRepository carRepository;

	private final CarMapper carMapper;

	public List<CarDTO> getAll() {
		//@formatter:off
		return carRepository.findAll()
					.stream()
					.map(carMapper::car_2_CarDTO)
					.collect(Collectors.toList());
		//@formatter:on
	}

	public Optional<CarDTO> getById(Long id) {
		return carRepository.findById(id).map(carMapper::car_2_CarDTO);
	}

	public CarDTO saveNewCar(NewCarDTO newCarDTO) {
		Car carSaved = carRepository.save(carMapper.newCarDto_2_Car(newCarDTO));
		return carMapper.car_2_CarDTO(carSaved);
	}
}
