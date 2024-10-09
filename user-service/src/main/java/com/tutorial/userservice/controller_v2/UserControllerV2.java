package com.tutorial.userservice.controller_v2;

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

import com.tutorial.userservice.controller_v2.dto.BikeRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.CarRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.NewUserRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.UserRestDtoV2;
import com.tutorial.userservice.controller_v2.mapper.BikeMapperRestV2;
import com.tutorial.userservice.controller_v2.mapper.CarMapperRestV2;
import com.tutorial.userservice.controller_v2.mapper.UserMapperRestV2;
import com.tutorial.userservice.service.UserService;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/user")
public class UserControllerV2 {

	private final UserService userService;

	final UserMapperRestV2 userMapperRestV2;

	final CarMapperRestV2 carMapperRestV2;

	final BikeMapperRestV2 bikeMapperRestV2;

	@GetMapping
	public ResponseEntity<List<UserRestDtoV2>> getAll() {
		//@formatter:off
		return new ResponseEntity<>(userService.getAll()
				.stream()
				.map(userMapperRestV2::userDto_2_UserRestDtoV2)
				.collect(Collectors.toList()), 
				HttpStatus.OK);
		//@formatter:on
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<UserDTO> userReturned = userService.getById(id);
		return userReturned
				.map(user -> ResponseEntity.ok(userMapperRestV2.userDto_2_UserRestDtoV2(user)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping()
	public ResponseEntity<UserRestDtoV2> saveNewUser(@RequestBody NewUserRestDtoV2 user) {
		try {
			NewUserDTO newUserDTO = userMapperRestV2.newUserRestDtoV2_2_NewUserDto(user);
			UserDTO userReturned = userService.saveNewUser(newUserDTO);
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userMapperRestV2.userDto_2_UserRestDtoV2(userReturned));

		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{userId}/cars")
	public ResponseEntity<List<CarRestDtoV2>> getCars(@PathVariable("userId") int userId) {
		//@formatter:off
		List<CarRestDtoV2> carsReturned = userService.getCarsByUserId((long) userId)
				.stream()
				.map(carMapperRestV2::carDTO_2_CarRestDtoV2)
				.collect(Collectors.toList());

		return carsReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(carsReturned);
		//@formatter:on
	}

	@GetMapping("/{userId}/bikes")
	public ResponseEntity<List<BikeRestDtoV2>> getBikes(@PathVariable("userId") int userId) {
		//@formatter:off
		List<BikeRestDtoV2> bikesReturned = userService.getBikesByUserId((long) userId)
				.stream()
				.map(bikeMapperRestV2::bikeDTO_2_BikeRestDtoV2)
				.collect(Collectors.toList());

		return bikesReturned.isEmpty() ? ResponseEntity.notFound().build()
				: ResponseEntity.ok(bikesReturned);
		//@formatter:on
	}

	// @GetMapping("/getAll/{userId}")
	// public ResponseEntity<Map<String, Object>>
	// getAllVehicles(@PathVariable("userId") int userId) {
	// Map<String, Object> result = userServiceImpl.getUserAndVehicles(userId);
	// return ResponseEntity.ok(result);
	// }

}
