// package com.tutorial.userservice.controller_v1.feignclient;

// import java.util.List;

// import org.springframework.cloud.openfeign.FeignClient;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;

// import com.tutorial.userservice.controller_v1.feignclient.model.Car;

// @FeignClient(name = "car-service", url = "http://localhost:8002/car")
// public interface CarFeignClient {

// @PostMapping
// Car save(@RequestBody Car car);

// @GetMapping("/byUser/{userId}")
// List<Car> getCars(@PathVariable("userId") int userId);
// }
