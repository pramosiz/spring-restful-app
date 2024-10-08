package com.tutorial.userservice.feignclient.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tutorial.userservice.feignclient.dto.CarFeignRestDtoV2;

@FeignClient(name = "car-service", url = "http://localhost:8002/api/v2/car")
public interface CarFeignClientV2 {

    @GetMapping("/byUser/{userId}")
    List<CarFeignRestDtoV2> getCarsByUserId(@PathVariable("userId") Long id);
}
