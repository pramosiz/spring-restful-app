package com.tutorial.bikeservice.adapters.inputs.api.controller_v2;

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

import com.tutorial.bikeservice.adapters.inputs.api.controller_v2.mapper.BikeMapperRestV2;
import com.tutorial.bikeservice.application.services.BikeService;
import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.controller.BikeControllerPort;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.ports.inputs.api.controller_v2.dto.NewBikeRestDtoV2;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bike")
@Slf4j
@Tag(name = "Bike Controller", description = "v2")
public class BikeControllerV2 implements BikeControllerPort {

	private final BikeService bikeService;

	private final BikeMapperRestV2 bikeMapperRestV2;

	@GetMapping
	@Operation(summary = "Get all Bikes", description = "Service to get all Bikes", responses = @ApiResponse(responseCode = "200", description = "Success"))
	public ResponseEntity<List<BikeRestDtoV2>> getAll() {
		//@formatter:off
		return new ResponseEntity<>(bikeService.getAll()
				.stream()
				.map(bikeMapperRestV2::bikeDTO_2_BikeRestDtoV2)
				.collect(Collectors.toList()), 
				HttpStatus.OK);
		//@formatter:on
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get Bike by ID", description = "Service to get 1 Bike", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "Bike not found") })
	public ResponseEntity<BikeRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<BikeDtoV2> bikeReturned = bikeService.getById(id);
		// @formatter:off
		return bikeReturned.map(bike -> ResponseEntity.ok(bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bike)))
				.orElseGet(() -> ResponseEntity.notFound().build());
		// @formatter:on
	}

	@PostMapping
	@Operation(summary = "Save new Bike", description = "Service to save new Bike", responses = {
			@ApiResponse(responseCode = "201", description = "Bike saved"),
			@ApiResponse(responseCode = "400", description = "Bad requested for save Bike") })
	public ResponseEntity<BikeRestDtoV2> saveNewBike(@RequestBody NewBikeRestDtoV2 newBikeRestDtoV2) {
		NewBikeDtoV2 newBikeDTO = bikeMapperRestV2.newBikeRestDtoV2_2_NewBikeDTO(newBikeRestDtoV2);
		Optional<BikeDtoV2> bikeReturned = bikeService.saveNewBikeWithExternalCheck(newBikeDTO);
		return bikeReturned
				.map(bike -> ResponseEntity.status(HttpStatus.CREATED)
						.body(bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bike)))
				.orElseGet(() -> {
					log.info("User doesn't exist with id: {}", newBikeRestDtoV2.getUserId());
					return ResponseEntity.badRequest().build();
				});
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete Bike by ID", description = "Service to erase Bike from DDBB", responses = @ApiResponse(responseCode = "204", description = "Bike erased"))
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		bikeService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/byUser/{userId}")
	@CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetBikesByUserId")
	@Operation(summary = "Get Bike by User", description = "Service to get Bikes by User", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "400", description = "Bad requested for getting Bikes") })
	public ResponseEntity<List<BikeRestDtoV2>> getByUserId(@PathVariable("userId") Long userId) {

		List<BikeRestDtoV2> bikesReturned = bikeService.getByUserId(userId).stream()
				.map(bikeMapperRestV2::bikeDTO_2_BikeRestDtoV2)
				.collect(Collectors.toList());
		return bikesReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(bikesReturned);
	}

	private ResponseEntity<List<BikeRestDtoV2>> fallbackGetBikesByUserId(Long userId, Exception e) {
		log.error("Error getting bikes by user id: {}", userId, e);
		return ResponseEntity.badRequest().build();
	}
}
