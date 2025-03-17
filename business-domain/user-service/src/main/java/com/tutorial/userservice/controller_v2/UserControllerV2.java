package com.tutorial.userservice.controller_v2;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for managing Users
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = { "${spring.security.cors.allowed-origins}" })
@RequestMapping("/api/v2/user")
@Tag(name = "User Controller", description = "v2")
public class UserControllerV2 {

	private final UserService userService;

	final UserMapperRestV2 userMapperRestV2;

	final CarMapperRestV2 carMapperRestV2;

	final BikeMapperRestV2 bikeMapperRestV2;

	/**
	 * Service to get all Users
	 * 
	 * @return Users list
	 */
	@GetMapping
	@Operation(summary = "Get all Users", description = "Service to get all Users", responses = @ApiResponse(responseCode = "200", description = "Success"))
	public ResponseEntity<List<UserRestDtoV2>> getAll() {
		//@formatter:off
		return new ResponseEntity<>(userService.getAll()
				.stream()
				.map(userMapperRestV2::userDto_2_UserRestDtoV2)
				.collect(Collectors.toList()), 
				HttpStatus.OK);
		
		// List<UserDTO> users = userService.getAll();
		// return users.isEmpty() ? ResponseEntity.noContent().build()
		// 		: ResponseEntity.ok(
		// 				users.stream()
		// 				.map(userMapperRestV2::userDto_2_UserRestDtoV2)
		// 				.collect(Collectors.toList()));
		//@formatter:on
	}

	/**
	 * Service to get 1 User by ID
	 * 
	 * @param id: User ID
	 * @return User information
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Get User by ID", description = "Service to get 1 User", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	public ResponseEntity<UserRestDtoV2> getById(@PathVariable("id") Long id) {
		Optional<UserDTO> userReturned = userService.getById(id);
		return userReturned
				.map(user -> ResponseEntity.ok(userMapperRestV2.userDto_2_UserRestDtoV2(user)))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Service to save new User
	 * 
	 * @param user: User information
	 * @return User information saved
	 */
	@PostMapping()
	@Operation(summary = "Save new User", description = "Service to save new User", responses = {
			@ApiResponse(responseCode = "201", description = "User saved"),
			@ApiResponse(responseCode = "400", description = "Bad requested for save User") })
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

	/**
	 * Service to delete User by ID
	 * 
	 * @param id: User ID
	 * @return No content
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Delete User by ID", description = "Service to erase User from DDBB", responses = @ApiResponse(responseCode = "204", description = "User erased"))
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
		userService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Service to get cars by User ID
	 * 
	 * @param userId: User ID
	 * @return Cars list by User
	 */
	@GetMapping("/{userId}/cars")
	@CircuitBreaker(name = "carService", fallbackMethod = "fallbackGetCarsByUserId")
	@Operation(summary = "Get cars by User ID", description = "Service to get user's cars", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "User not found") })
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

	private ResponseEntity<List<CarRestDtoV2>> fallbackGetCarsByUserId(int userId, Throwable t) {
		log.warn("The user with userId {} hasn't got cars", userId, t);
		return ResponseEntity.notFound().build();
	}

	/**
	 * Service to get bikes by User ID
	 * 
	 * @param userId: User ID
	 * @return Bikes list by User
	 */
	@GetMapping("/{userId}/bikes")
	@CircuitBreaker(name = "bikeService", fallbackMethod = "fallbackGetBikesByUserId")
	@Operation(summary = "Get bikes by User ID", description = "Service to get user's bikes", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "User not found") })
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

	private ResponseEntity<List<BikeRestDtoV2>> fallbackGetBikesByUserId(int userId, Throwable t) {
		log.warn("The user with userId {} hasn't got bikes", userId, t);
		return ResponseEntity.notFound().build();
	}

	/**
	 * Get all vehicles by User ID
	 * 
	 * @param userId: User ID
	 * @return User and vehicles
	 */
	@GetMapping("/getAll/{userId}")
	@CircuitBreaker(name = "vehicleService", fallbackMethod = "fallbackGetAllVehiclesByUserId")
	@Operation(summary = "Get vehicles by User ID", description = "Service to get user's vehicles", responses = {
			@ApiResponse(responseCode = "200", description = "Success"),
			@ApiResponse(responseCode = "404", description = "User not found") })
	public ResponseEntity<Map<String, Object>> getAllVehicles(@PathVariable("userId") Long userId) {
		try {
			return ResponseEntity.ok(userService.getUserAndVehicles(userId));
		} catch (RuntimeException e) {
			return ResponseEntity.notFound().build();
		}
	}

	private ResponseEntity<Map<String, Object>> fallbackGetAllVehiclesByUserId(Long userId, Throwable t) {
		log.warn("The user with userId {} hasn't got vehicles", userId, t);
		return ResponseEntity.notFound().build();
	}
}
