package com.tutorial.userservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewUserDTO implements Serializable {

    @JsonProperty
    private String name;

    @JsonProperty
    private String email;
}
