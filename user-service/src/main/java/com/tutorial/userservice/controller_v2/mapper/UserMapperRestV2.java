package com.tutorial.userservice.controller_v2.mapper;

import org.mapstruct.Mapper;

import com.tutorial.userservice.controller_v2.dto.NewUserRestDtoV2;
import com.tutorial.userservice.controller_v2.dto.UserRestDtoV2;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapperRestV2 {

    UserRestDtoV2 userDto_2_UserRestDtoV2(UserDTO userDto);

    UserDTO userRestDtoV2_2_UserDto(UserRestDtoV2 user);

    NewUserDTO newUserRestDtoV2_2_NewUserDto(NewUserRestDtoV2 user);
}
