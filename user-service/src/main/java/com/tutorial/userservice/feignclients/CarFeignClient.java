package com.tutorial.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tutorial.userservice.model.Car;

@FeignClient(name = "car-service")		// Quitamos el url porque ya está registrado en 'eureka'
public interface CarFeignClient {

	@PostMapping("/car")
	Car save(@RequestBody Car car);
	
	@GetMapping("/car/byUser/{userId}")
	List<Car> getCars(@PathVariable("userId") int userId);
}
