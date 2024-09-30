package com.tutorial.userservice.controller_v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserControllerV1 {

	// @Autowired
	// UserServiceImpl userServiceImpl;

	// @Autowired
	// UserService userService;

	// @GetMapping
	// public ResponseEntity<List<UserRestDtoV2>> getAll() {
	// return userService.getAll();
	// }

	// @GetMapping("/{id}")
	// public ResponseEntity<User> getById(@PathVariable("id") int id) {
	// User user = userServiceImpl.getUserById(id);
	// if(user == null) {
	// return ResponseEntity.notFound().build();
	// }
	// return ResponseEntity.ok(user);
	// }

	// @PostMapping()
	// public ResponseEntity<User> save(@RequestBody User user) {
	// User userNew = userServiceImpl.save(user);
	// return ResponseEntity.ok(userNew);
	// }

	// @GetMapping("/cars/{userId}")
	// public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId)
	// {
	// User user = userServiceImpl.getUserById(userId);
	// if(user == null) {
	// return ResponseEntity.notFound().build();
	// }
	// List<Car> cars = userServiceImpl.getCars(userId);
	// return ResponseEntity.ok(cars);
	// }

	// @GetMapping("/bikes/{userId}")
	// public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int
	// userId) {
	// User user = userServiceImpl.getUserById(userId);
	// if(user == null) {
	// return ResponseEntity.notFound().build();
	// }
	// List<Bike> bikes = userServiceImpl.getBikes(userId);
	// return ResponseEntity.ok(bikes);
	// }

	// @PostMapping("/saveCar/{userId}")
	// public ResponseEntity<Car> saveCar(@PathVariable("userId") int userId,
	// @RequestBody Car car) {
	// if(userServiceImpl.getUserById(userId) == null) {
	// return ResponseEntity.notFound().build();
	// }
	// Car carNew = userServiceImpl.saveCar(userId, car);
	// return ResponseEntity.ok(carNew);
	// }

	// @PostMapping("/saveBike/{userId}")
	// public ResponseEntity<Bike> saveBike(@PathVariable("userId") int userId,
	// @RequestBody Bike bike) {
	// if(userServiceImpl.getUserById(userId) == null) {
	// return ResponseEntity.notFound().build();
	// }
	// Bike bikeNew = userServiceImpl.saveBike(userId, bike);
	// return ResponseEntity.ok(bikeNew);
	// }

	// @GetMapping("/getAll/{userId}")
	// public ResponseEntity<Map<String, Object>> getAllVehicles
	// (@PathVariable("userId") int userId) {
	// Map<String, Object> result = userServiceImpl.getUserAndVehicles(userId);
	// return ResponseEntity.ok(result);
	// }

}
