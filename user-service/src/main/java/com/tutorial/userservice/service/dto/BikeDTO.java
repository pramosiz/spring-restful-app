package com.tutorial.userservice.service.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BikeDTO implements Serializable {

    @JsonProperty
    private Long id;

    @JsonProperty
    private String brand;

    @JsonProperty
    private String model;

    @JsonProperty("user_id")
    private Long userId;
}
