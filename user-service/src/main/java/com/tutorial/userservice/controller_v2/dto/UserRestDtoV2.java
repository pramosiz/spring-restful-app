package com.tutorial.userservice.controller_v2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(name = "UserRestDtoV2", description = "User Data Transfer Object V2")
public class UserRestDtoV2 implements Serializable {

    //@formatter:off
    @JsonProperty
    @Schema(
        description = "User identifier",
        name = "id",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1")
    private Long id;

    @JsonProperty
    @Schema(
        description = "User name",
        name = "name",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "John Doe")
    private String name;

    @JsonProperty
    @Schema(
        description = "User email",
        name = "email",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "johndoe@gmail.com")
    private String email;
    //@formatter:on
}
