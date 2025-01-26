package com.tutorial.bikeservice.feignclient.clients;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tutorial.bikeservice.feignclient.dto.UserRestDtoV2;

// @FeignClient(name = "user-service", url = "http://localhost:8001/api/v2/user")
@FeignClient(name = "user-service")
public interface UserFeignClientV2 {

    @GetMapping("/api/v2/user/{id}")
    Optional<UserRestDtoV2> getById(@PathVariable("id") Long id);
}
