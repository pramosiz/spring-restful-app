package com.tutorial.carservice.controller_v2;

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

import com.tutorial.carservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.carservice.controller_v2.dto.NewCarRestDtoV2;
import com.tutorial.carservice.controller_v2.mapper.CarMapperRestV2;
import com.tutorial.carservice.service.CarService;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/car")
@Slf4j
public class CarControllerV2 {

	private final CarService carService;

	private final CarMapperRestV2 carMapperRestV2;

	@GetMapping
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
	public ResponseEntity<CarRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<CarDTO> carReturned = carService.getById(id);
		return carReturned.map(car -> ResponseEntity.ok(carMapperRestV2.carDTO_2_CarRestDtoV2(car)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		carService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping()
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

	@GetMapping("/byUser/{userId}")
	public ResponseEntity<List<CarRestDtoV2>> getByUserId(@PathVariable("userId") Long userId) {

		List<CarRestDtoV2> carsReturned = carService.getByUserId(userId).stream()
				.map(carMapperRestV2::carDTO_2_CarRestDtoV2)
				.collect(Collectors.toList());
		return carsReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(carsReturned);
	}
}
