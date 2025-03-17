package com.tutorial.carservice.application.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.carservice.adapters.outputs.feign.utils.FeignUtils;
import com.tutorial.carservice.application.services.CarService;
import com.tutorial.carservice.domain.V2.dto.CarDTO;
import com.tutorial.carservice.domain.V2.dto.NewCarDTO;
import com.tutorial.carservice.domain.V2.mapper.CarMapperV2;
import com.tutorial.carservice.domain.V2.model.Car;
import com.tutorial.carservice.ports.outputs.feign.clients.UserFeignClientV2;
import com.tutorial.carservice.ports.outputs.persistence.repositories.CarRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

	private final CarRepositoryPort carRepository;

	private final CarMapperV2 carMapper;

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
		return FeignUtils.safeFeignCall(() -> userFeignClient.getById(newCarDTO.getUserId())
				.map(userRestDto -> {
					Car carSaved = carRepository.save(carMapper.newCarDto_2_Car(newCarDTO));
					return carMapper.car_2_CarDTO(carSaved);
				}).orElseThrow(() -> new RuntimeException("User not found")));
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
