package com.tutorial.userservice.feignclient.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tutorial.userservice.feignclient.dto.BikeFeignRestDtoV2;

// @FeignClient(name = "bike-service", url = "${services.bike-service.path}/api/v2/bike")
@FeignClient(name = "bike-service")
public interface BikeFeignClientV2 {

    @GetMapping("/api/v2/bike/byUser/{userId}")
    List<BikeFeignRestDtoV2> getBikesByUserId(@PathVariable("userId") Long id);
}
