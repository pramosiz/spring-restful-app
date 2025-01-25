package com.tutorial.userservice.controller_v2.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.controller_v2.dto.NewUserRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.UserRestDtoV2;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

@Service
public class UserMapperRestV2 {

    public UserRestDtoV2 userDto_2_UserRestDtoV2(UserDTO userDto) {
        if (userDto == null) {
            return null;
        } else {
            return UserRestDtoV2.builder()
                    .id(userDto.getId())
                    .name(userDto.getName())
                    .email(userDto.getEmail())
                    .build();
        }
    }

    public UserDTO userRestDtoV2_2_UserDto(UserRestDtoV2 user) {
        if (user == null) {
            return null;
        } else {
            return UserDTO.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }

    public NewUserDTO newUserRestDtoV2_2_NewUserDto(NewUserRestDtoV2 user) {
        if (user == null) {
            return null;
        } else {
            return NewUserDTO.builder()
                    .name(user.getName())
                    .email(user.getEmail())
                    .build();
        }
    }
}
