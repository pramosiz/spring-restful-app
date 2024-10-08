package com.tutorial.userservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tutorial.userservice.feignclient.clients.BikeFeignClientV2;
import com.tutorial.userservice.feignclient.clients.CarFeignClientV2;
import com.tutorial.userservice.repository.entities.User;
import com.tutorial.userservice.repository.repositories.UserRepository;
import com.tutorial.userservice.service.UserService;
import com.tutorial.userservice.service.dto.BikeDTO;
import com.tutorial.userservice.service.dto.CarDTO;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;
import com.tutorial.userservice.serviceimpl.mapper.BikeMapper;
import com.tutorial.userservice.serviceimpl.mapper.CarMapper;
import com.tutorial.userservice.serviceimpl.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	final UserMapper userMapper;

	final CarFeignClientV2 carFeignClientV2;

	final CarMapper carMapper;

	final BikeFeignClientV2 bikeFeignClientV2;

	final BikeMapper bikeMapper;

	public List<UserDTO> getAll() {
		//@formatter:off
		return userRepository.findAll()
					.stream()
					.map(userMapper::user_2_UserDto)
					.collect(Collectors.toList());
		//@formatter:on
	}

	public Optional<UserDTO> getById(Long id) {
		//@formatter:off
		return userRepository.findById(id)
					.map(userMapper::user_2_UserDto);
		//@formatter:on
	}

	public UserDTO saveNewUser(NewUserDTO newUserDto) {

		User userSaved = userRepository.save(userMapper.newUserDto_2_User(newUserDto));
		return userMapper.user_2_UserDto(userSaved);
	}

	@Override
	public List<CarDTO> getCarsByUserId(Long userId) {
		return carFeignClientV2.getCarsByUserId(userId)
				.stream()
				.map(carMapper::carFeignRestDtoV2_2_CarDTO)
				.collect(Collectors.toList());
	}

	@Override
	public List<BikeDTO> getBikesByUserId(Long userId) {
		return bikeFeignClientV2.getBikesByUserId(userId)
				.stream()
				.map(bikeMapper::bikeFeignRestDtoV2_2_BikeDTO)
				.collect(Collectors.toList());
	}
}
