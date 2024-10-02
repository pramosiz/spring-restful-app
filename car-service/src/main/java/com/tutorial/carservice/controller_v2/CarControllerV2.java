package com.tutorial.carservice.controller_v2;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/car")
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
		// @formatter:off
		return carReturned.map(car -> new ResponseEntity<>(
						carMapperRestV2.carDTO_2_CarRestDtoV2(car), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		// @formatter:on
	}

	@PostMapping()
	public ResponseEntity<CarRestDtoV2> saveNewBike(@RequestBody NewCarRestDtoV2 newCarRestDtoV2) {
		NewCarDTO newCarDTO = carMapperRestV2.newCarRestDtoV2_2_NewCarDTO(newCarRestDtoV2);
		CarDTO bikeReturned = carService.saveNewCar(newCarDTO);
		return new ResponseEntity<>(carMapperRestV2.carDTO_2_CarRestDtoV2(bikeReturned), HttpStatus.CREATED);
	}

	// @GetMapping("/byUser/{userId}")
	// public ResponseEntity<List<Car>> getByUserId(@PathVariable("userId") int
	// userId) {
	// List<Car> cars = carService.byUserId(userId);
	// return ResponseEntity.ok(cars);
	// }

}
