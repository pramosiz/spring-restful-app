package com.tutorial.carservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.feignclient.clients.UserFeignClientV2;
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

	private final UserFeignClientV2 userFeignClient;

	public List<CarDTO> getAll() {
		return carRepository.findAll()
				.stream()
				.map(carMapper::car_2_CarDTO)
				.collect(Collectors.toList());
	}

	public Optional<CarDTO> getById(Long id) {
		return carRepository.findById(id).map(carMapper::car_2_CarDTO);
	}

	public Optional<CarDTO> saveNewCarWithExternalCheck(NewCarDTO newCarDTO) {
		if (userFeignClient.getById(newCarDTO.getUserId()).isPresent()) {
			Car carSaved = carRepository.save(carMapper.newCarDto_2_Car(newCarDTO));
			return Optional.of(carMapper.car_2_CarDTO(carSaved));
		} else {
			return Optional.empty();
		}
	}

	public List<CarDTO> getByUserId(Long id) {
		return carRepository.findByUserId(id)
				.stream()
				.map(carMapper::car_2_CarDTO)
				.collect(Collectors.toList());
	}

	public void deleteById(Long id) {
		carRepository.deleteById(id);
	}

	public void deleteByUserId(Long id) {
		carRepository.deleteByUserId(id);
	}
}
