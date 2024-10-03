package com.tutorial.userservice.serviceimpl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tutorial.userservice.repository.entities.User;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO user_2_UserDto(User user);

    @Mapping(target = "id", ignore = true)
    User newUserDto_2_User(NewUserDTO newUserDto);
}
