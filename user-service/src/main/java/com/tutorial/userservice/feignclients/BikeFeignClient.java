package com.tutorial.userservice.feignclients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.tutorial.userservice.model.Bike;

@FeignClient(name = "bike-service")	// Quitamos el url porque ya está registrado en 'eureka'
public interface BikeFeignClient {

	@PostMapping("/bike")
	Bike save(@RequestBody Bike bike);
	
	@GetMapping("/bike/byUser/{userId}")
	List<Bike> getBikes(@PathVariable("userId") int userId);
}
