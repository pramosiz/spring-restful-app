package com.tutorial.carservice.adapters.inputs.api.controller_v2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.carservice.adapters.inputs.api.controller_v2.mapper.CarMapperRestV2;
import com.tutorial.carservice.application.services.CarService;
import com.tutorial.carservice.domain.V2.dto.CarDTO;
import com.tutorial.carservice.domain.V2.dto.NewCarDTO;
import com.tutorial.carservice.ports.inputs.api.controller_v2.controller.CarControllerPort;
import com.tutorial.carservice.ports.inputs.api.controller_v2.dto.CarRestDtoV2;
import com.tutorial.carservice.ports.inputs.api.controller_v2.dto.NewCarRestDtoV2;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/car")
@Slf4j
@Tag(name = "Car Controller", description = "v2")
public class CarControllerV2 implements CarControllerPort {

	private final CarService carService;

	private final CarMapperRestV2 carMapperRestV2;

	@GetMapping
	@Operation(summary = "Get all Cars", description = "Service to get all Cars", responses = @ApiResponse(responseCode = "200", description = "Success"))
	public ResponseEntity<List<CarRestDtoV2>> getAll() {
		//@formatter:off
		return new ResponseEntity<>(carService.getAll()
				.stream()
				.map(carMapperRestV2::carDTO_2_CarRestDtoV2)
				.collect(Collectors.toList()), 
				HttpStatus.OK);
		//@formatter:on
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Car by ID", description = "Service to get 1 Car", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Car not found") })
	public ResponseEntity<CarRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<CarDTO> carReturned = carService.getById(id);
		return carReturned.map(car -> ResponseEntity.ok(carMapperRestV2.carDTO_2_CarRestDtoV2(car)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping
	@Operation(summary = "Save new Car", description = "Service to save new Car", responses = {
			@ApiResponse(responseCode = "201", description = "Car saved"),
			@ApiResponse(responseCode = "400", description = "Bad requested for save Car") })
	public ResponseEntity<CarRestDtoV2> saveNewBike(@RequestBody NewCarRestDtoV2 newCarRestDtoV2) {
		NewCarDTO newCarDTO = carMapperRestV2.newCarRestDtoV2_2_NewCarDTO(newCarRestDtoV2);
		Optional<CarDTO> carReturned = carService.saveNewCarWithExternalCheck(newCarDTO);
		return carReturned
				.map(car -> ResponseEntity.status(HttpStatus.CREATED).body(carMapperRestV2.carDTO_2_CarRestDtoV2(car)))
				.orElseGet(() -> {
					log.info("User doesn't exist with id: {}", newCarRestDtoV2.getUserId());
					return ResponseEntity.badRequest().build();
				});
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Car by ID", description = "Service to erase Car from DDBB", responses = @ApiResponse(responseCode = "204", description = "Car erased"))
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		carService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/byUser/{userId}")
	@CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetCarsByUserId")
	@Operation(summary = "Get Car by User", description = "Service to get Cars by User", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad requested for getting Cars") })
	public ResponseEntity<List<CarRestDtoV2>> getByUserId(@PathVariable("userId") Long userId) {

		List<CarRestDtoV2> carsReturned = carService.getByUserId(userId).stream()
				.map(carMapperRestV2::carDTO_2_CarRestDtoV2)
				.collect(Collectors.toList());
		return carsReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(carsReturned);
	}

	private ResponseEntity<List<CarRestDtoV2>> fallbackGetCarsByUserId(Long userId, Exception e) {
		log.error("Error getting Cars by User: {}", e.getMessage());
		return ResponseEntity.badRequest().build();
	}
}
