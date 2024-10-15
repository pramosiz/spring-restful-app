package com.tutorial.userservice.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.userservice.amqp.dto.NotificationDTO;
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
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	final UserMapper userMapper;

	final CarFeignClientV2 carFeignClientV2;

	final CarMapper carMapper;

	final BikeFeignClientV2 bikeFeignClientV2;

	final BikeMapper bikeMapper;

	final RabbitTemplate rabbitTemplate;

	final FanoutExchange notifyDeleteInfoFanout;

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

	public List<CarDTO> getCarsByUserId(Long userId) {
		return carFeignClientV2.getCarsByUserId(userId)
				.stream()
				.map(carMapper::carFeignRestDtoV2_2_CarDTO)
				.collect(Collectors.toList());
	}

	public List<BikeDTO> getBikesByUserId(Long userId) {
		return bikeFeignClientV2.getBikesByUserId(userId)
				.stream()
				.map(bikeMapper::bikeFeignRestDtoV2_2_BikeDTO)
				.collect(Collectors.toList());
	}

	public void deleteById(Long id) {
		try {
			String message = new ObjectMapper().writeValueAsString(new NotificationDTO(id));
			userRepository.deleteById(id);
			rabbitTemplate.convertAndSend(notifyDeleteInfoFanout.getName(), "", message);
		} catch (Exception e) {
			log.error("Failed to delete user with id: {}", id, e);
		}
	}
}
