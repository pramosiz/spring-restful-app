package com.tutorial.userservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tutorial.userservice.entity.User;
import com.tutorial.userservice.model.Bike;
import com.tutorial.userservice.model.Car;
import com.tutorial.userservice.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping
	public ResponseEntity<List<User>> getAll() {
		List<User> users = userService.getAll();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> getById(@PathVariable("id") int id) {
		User user = userService.getUserById(id);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(user);
	}
	
	@PostMapping()
	public ResponseEntity<User> save(@RequestBody User user) {
		User userNew = userService.save(user);
		return ResponseEntity.ok(userNew);
	}
	
	@GetMapping("/cars/{userId}")
	public ResponseEntity<List<Car>> getCars(@PathVariable("userId") int userId) {
		User user = userService.getUserById(userId);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		List<Car> cars = userService.getCars(userId);
		return ResponseEntity.ok(cars);
	}
	
	@GetMapping("/bikes/{userId}")
	public ResponseEntity<List<Bike>> getBikes(@PathVariable("userId") int userId) {
		User user = userService.getUserById(userId);
		if(user == null) {
			return ResponseEntity.notFound().build();
		}
		List<Bike> bikes = userService.getBikes(userId);
		return ResponseEntity.ok(bikes);
	}
}
