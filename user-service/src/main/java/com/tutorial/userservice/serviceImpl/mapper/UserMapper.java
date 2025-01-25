package com.tutorial.userservice.serviceimpl.mapper;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.repository.entities.User;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

@Service
public class UserMapper {

    public UserDTO user_2_UserDto(User user) {
        if (user == null) {
            return null;
        } else {
            UserDTO userDto = new UserDTO();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            return userDto;
        }
    }

    public User newUserDto_2_User(NewUserDTO newUserDto) {
        if (newUserDto == null) {
            return null;
        } else {
            User user = new User();
            user.setName(newUserDto.getName());
            user.setEmail(newUserDto.getEmail());
            return user;
        }
    }
}
