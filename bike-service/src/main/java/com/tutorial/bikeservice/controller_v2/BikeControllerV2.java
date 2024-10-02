package com.tutorial.bikeservice.controller_v2;

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

import com.tutorial.bikeservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.dto.NewBikeRestDtoV2;
import com.tutorial.bikeservice.controller_v2.mapper.BikeMapperRestV2;
import com.tutorial.bikeservice.service.BikeService;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bike")
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
		return bikeReturned.map(bike -> new ResponseEntity<>(
						bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bike), HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
		// @formatter:on
	}

	@PostMapping()
	public ResponseEntity<BikeRestDtoV2> saveNewBike(@RequestBody NewBikeRestDtoV2 newBikeRestDtoV2) {
		NewBikeDTO newBikeDTO = bikeMapperRestV2.newBikeRestDtoV2_2_NewBikeDTO(newBikeRestDtoV2);
		BikeDTO bikeReturned = bikeService.saveNewBike(newBikeDTO);
		return new ResponseEntity<>(bikeMapperRestV2.bikeDTO_2_BikeRestDtoV2(bikeReturned), HttpStatus.CREATED);
	}

	// @GetMapping("/byUser/{userId}")
	// public ResponseEntity<List<Bike>> getByUserId(@PathVariable("userId") int
	// userId) {
	// List<Bike> bikes = bikeService.byUserId(userId);
	// return ResponseEntity.ok(bikes);
	// }

}
