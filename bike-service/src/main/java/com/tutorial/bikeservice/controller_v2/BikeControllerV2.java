package com.tutorial.bikeservice.controller_v2;

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

import com.tutorial.bikeservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.dto.NewBikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.mapper.BikeMapperRestV2;
import com.tutorial.bikeservice.service.BikeService;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bike")
@Slf4j
public class BikeControllerV2 {

	private final BikeService bikeService;

	private final BikeMapperRestV2 bikeMapperRestV2;

	@GetMapping
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
	public ResponseEntity<BikeRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<BikeDTO> bikeReturned = bikeService.getById(id);
		// @formatter:off
		return bikeReturned.map(bike -> ResponseEntity.ok(bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bike)))
				.orElseGet(() -> ResponseEntity.notFound().build());
		// @formatter:on
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		bikeService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping()
	public ResponseEntity<BikeRestDtoV2> saveNewBike(@RequestBody NewBikeRestDtoV2 newBikeRestDtoV2) {
		NewBikeDTO newBikeDTO = bikeMapperRestV2.newBikeRestDtoV2_2_NewBikeDTO(newBikeRestDtoV2);
		Optional<BikeDTO> bikeReturned = bikeService.saveNewBikeWithExternalCheck(newBikeDTO);
		return bikeReturned
				.map(bike -> ResponseEntity.status(HttpStatus.CREATED)
						.body(bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bike)))
				.orElseGet(() -> {
					log.info("User doesn't exist with id: {}", newBikeRestDtoV2.getUserId());
					return ResponseEntity.badRequest().build();
				});
	}

	@GetMapping("/byUser/{userId}")
	public ResponseEntity<List<BikeRestDtoV2>> getByUserId(@PathVariable("userId") Long userId) {

		List<BikeRestDtoV2> bikesReturned = bikeService.getByUserId(userId).stream()
				.map(bikeMapperRestV2::bikeDTO_2_BikeRestDtoV2)
				.collect(Collectors.toList());
		return bikesReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(bikesReturned);
	}
}
