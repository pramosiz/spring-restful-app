package com.tutorial.userservice.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tutorial.userservice.controller_v1.feignclient.BikeFeignClient;
import com.tutorial.userservice.controller_v1.feignclient.CarFeignClient;
import com.tutorial.userservice.controller_v1.feignclient.model.Bike;
import com.tutorial.userservice.controller_v1.feignclient.model.Car;
import com.tutorial.userservice.controller_v2.dto.UserRestDtoV2;
import com.tutorial.userservice.repository.domains.User;
import com.tutorial.userservice.repository.repositories.UserRepository;
import com.tutorial.userservice.service.UserService;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;
import com.tutorial.userservice.serviceImpl.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	final UserMapper userMapper;

	public List<UserDTO> getAll() {
		//@formatter:off
		return userRepository.findAll()
					.stream()
					.map(userMapper::user_2_UserDto)
					.collect(Collectors.toList());
		//@formatter:on
	}

	public Optional<UserDTO> getById(Long id) {
		//@formatter:off
		return userRepository.findById(id)
					.map(userMapper::user_2_UserDto);
		//@formatter:on
	}

	public UserDTO saveNewUser(NewUserDTO newUserDto) {

		User userSaved = userRepository.save(userMapper.newUserDto_2_User(newUserDto));
		return userMapper.user_2_UserDto(userSaved);
	}

	// public List<Car> getCars(int userId) {
	// List<Car> cars =
	// restTemplate.getForObject("http://localhost:8002/car/byUser/" + userId,
	// List.class);
	// return cars;
	// }

	// public List<Bike> getBikes(int userId) {
	// List<Bike> bikes =
	// restTemplate.getForObject("http://localhost:8003/bike/byUser/" + userId,
	// List.class);
	// return bikes;
	// }

	// public Car saveCar(int userId, Car car) {
	// car.setUserId(userId);
	// Car carNew = carFeignClient.save(car);
	// return carNew;
	// }

	// public Bike saveBike(int userId, Bike bike) {
	// bike.setUserId(userId);
	// Bike bikeNew = bikeFeignClient.save(bike);
	// return bikeNew;
	// }

	// public Map<String, Object> getUserAndVehicles(int userId) {
	// Map<String, Object> result = new HashMap<>();
	// User user = userRepository.findById(userId).orElse(null);
	// if (user == null) {
	// result.put("Mensaje", "no existe el usuario");
	// return result;
	// }
	// result.put("User", user);
	// List<Car> cars = carFeignClient.getCars(userId);
	// if (cars.isEmpty()) {
	// result.put("Cars", "ese user no tiene coches");
	// } else {
	// result.put("Cars", cars);
	// }
	// List<Bike> bikes = bikeFeignClient.getBikes(userId);
	// if (bikes.isEmpty()) {
	// result.put("Bikes", "ese user no tiene motos");
	// } else {
	// result.put("Bikes", bikes);
	// }
	// return result;
	// }

}
