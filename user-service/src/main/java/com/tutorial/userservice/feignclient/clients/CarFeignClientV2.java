package com.tutorial.userservice.feignclient.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tutorial.userservice.feignclient.dto.CarFeignRestDtoV2;

// @FeignClient(name = "car-service", url = "${services.car-service.path}/api/v2/car")
@FeignClient(name = "car-service")
public interface CarFeignClientV2 {

    @GetMapping("/api/v2/car/byUser/{userId}")
    List<CarFeignRestDtoV2> getCarsByUserId(@PathVariable("userId") Long id);
}
