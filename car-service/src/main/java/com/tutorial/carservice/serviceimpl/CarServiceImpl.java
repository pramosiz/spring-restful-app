package com.tutorial.carservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.tutorial.carservice.repository.entities.Car;
import com.tutorial.carservice.repository.repositories.CarRepository;
import com.tutorial.carservice.service.CarService;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;
import com.tutorial.carservice.serviceimpl.mapper.CarMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
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

	public Mono<CarDTO> saveNewCarWithExternalCheck(NewCarDTO newCarDTO) {
		//@formatter:off
		return WebClient.create("http://localhost:8001/api/v2/user")
				.get() // GET
				.uri("/{user_id}", newCarDTO.getUserId())
				.retrieve() // Send the request
				.onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
					log.info("User not found");
					return Mono.empty();
				})
				.onStatus(HttpStatusCode::is5xxServerError, clientResponse -> {
					log.info("User service error");
					return Mono.empty();
				})
				.bodyToMono(Void.class) // Response body
				.flatMap(nullValue -> {				 // Cast the response body to a Void object
					Car carSaved = carRepository.save(carMapper.newCarDto_2_Car(newCarDTO));
					return Mono.just(carMapper.car_2_CarDTO(carSaved));
				});
		//@formatter:on
	}
}
