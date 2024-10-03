package com.tutorial.userservice.controller_v2;

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

import com.tutorial.userservice.controller_v2.dto.NewUserRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.UserRestDtoV2;
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
				.map(user -> new ResponseEntity<>(userMapperRestV2.userDto_2_UserRestDtoV2(user),
						HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping()
	public ResponseEntity<UserRestDtoV2> saveNewUser(@RequestBody NewUserRestDtoV2 user) {
		try {
			NewUserDTO newUserDTO = userMapperRestV2.newUserRestDtoV2_2_NewUserDto(user);
			UserDTO userReturned = userService.saveNewUser(newUserDTO);
			return new ResponseEntity<>(userMapperRestV2.userDto_2_UserRestDtoV2(userReturned), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// @GetMapping("/cars/{userId}")
	// public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId)
	// {
	// User user = userServiceImpl.getUserById(userId);
	// if (user == null) {
	// return ResponseEntity.notFound().build();
	// }
	// List<Car> cars = userServiceImpl.getCars(userId);
	// return ResponseEntity.ok(cars);
	// }

	// @GetMapping("/bikes/{userId}")
	// public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int
	// userId) {
	// User user = userServiceImpl.getUserById(userId);
	// if (user == null) {
	// return ResponseEntity.notFound().build();
	// }
	// List<Bike> bikes = userServiceImpl.getBikes(userId);
	// return ResponseEntity.ok(bikes);
	// }

	// @PostMapping("/saveCar/{userId}")
	// public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId,
	// @RequestBody Car car) {
	// if (userServiceImpl.getUserById(userId) == null) {
	// return ResponseEntity.notFound().build();
	// }
	// Car carNew = userServiceImpl.saveCar(userId, car);
	// return ResponseEntity.ok(carNew);
	// }

	// @PostMapping("/saveBike/{userId}")
	// public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId,
	// @RequestBody Bike bike) {
	// if (userServiceImpl.getUserById(userId) == null) {
	// return ResponseEntity.notFound().build();
	// }
	// Bike bikeNew = userServiceImpl.saveBike(userId, bike);
	// return ResponseEntity.ok(bikeNew);
	// }

	// @GetMapping("/getAll/{userId}")
	// public ResponseEntity<Map<String, Object>>
	// getAllVehicles(@PathVariable("userId") int userId) {
	// Map<String, Object> result = userServiceImpl.getUserAndVehicles(userId);
	// return ResponseEntity.ok(result);
	// }

}
