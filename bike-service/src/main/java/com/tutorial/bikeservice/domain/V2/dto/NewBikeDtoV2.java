package com.tutorial.bikeservice.domain.V2.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "NewBikeDTO", description = "New Bike Data Transfer Object")
public class NewBikeDtoV2 implements Serializable {

    //@formatter:off
    @JsonProperty
    @Schema(
        description = "Bike brand", 
        name = "brand", 
        requiredMode = Schema.RequiredMode.REQUIRED, 
        example = "Honda")
    private String brand;

    @JsonProperty
    @Schema(
        description = "Bike model", 
        name = "model", 
        requiredMode = Schema.RequiredMode.REQUIRED, 
        example = "CBR 1000 RR")
    private String model;

    @JsonProperty("user_id")
    @Schema(
        description = "User identifier", 
        name = "userId", 
        requiredMode = Schema.RequiredMode.REQUIRED, 
        example = "1")
    private Long userId;
    //@formatter:on
}
