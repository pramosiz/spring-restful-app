package com.tutorial.bikeservice.controller_v2.dto;

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
public class NewBikeRestDtoV2 implements Serializable {

    @JsonProperty
    private String brand;

    @JsonProperty
    private String model;

    @JsonProperty("user_id")
    private Long userId;
}
