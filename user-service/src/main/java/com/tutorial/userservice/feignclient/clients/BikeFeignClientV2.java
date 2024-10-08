package com.tutorial.userservice.feignclient.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tutorial.userservice.feignclient.dto.BikeFeignRestDtoV2;

@FeignClient(name = "bike-service", url = "http://localhost:8003/api/v2/bike")
public interface BikeFeignClientV2 {

    @GetMapping("/byUser/{userId}")
    List<BikeFeignRestDtoV2> getBikesByUserId(@PathVariable("userId") Long id);
}
