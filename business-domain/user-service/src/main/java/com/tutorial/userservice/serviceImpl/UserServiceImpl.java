package com.tutorial.userservice.serviceImpl;

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
import com.tutorial.userservice.serviceimpl.utils.FeignUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // final RestTemplate restTemplate;

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
        // public List getCarsByUserId(Long userId) {
        // Jwt jwt = (Jwt)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Authorization", "Bearer " + jwt.getTokenValue());
        // ResponseEntity<List> cars =
        // restTemplate.exchange("http://car-service/api/v2/car/byUser/" + userId,
        // HttpMethod.GET, new HttpEntity<>(headers), List.class);
        // return cars.getBody();

        return FeignUtils.safeFeignCall(() -> carFeignClientV2.getCarsByUserId(userId)
                .stream()
                .map(carMapper::carFeignRestDtoV2_2_CarDTO)
                .collect(Collectors.toList()));
    }

    public List<BikeDTO> getBikesByUserId(Long userId) {

        // REST TEMPLATE
        // public List getBikesByUserId(Long userId) {
        // Jwt jwt = (Jwt)
        // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // HttpHeaders headers = new HttpHeaders();
        // headers.add("Authorization", "Bearer " + jwt.getTokenValue());
        // ResponseEntity<List> bikes =
        // restTemplate.exchange("http://bike-service/api/v2/bike/byUser/" + userId,
        // HttpMethod.GET, new HttpEntity<>(headers), List.class);
        // return bikes.getBody();

        return FeignUtils.safeFeignCall(() -> bikeFeignClientV2.getBikesByUserId(userId)
                .stream()
                .map(bikeMapper::bikeFeignRestDtoV2_2_BikeDTO)
                .collect(Collectors.toList()));
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

    public Map<String, Object> getUserAndVehicles(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        List<CarDTO> cars = FeignUtils.safeFeignCall(() -> carFeignClientV2.getCarsByUserId(userId)
                .stream()
                .map(carMapper::carFeignRestDtoV2_2_CarDTO)
                .collect(Collectors.toList()));
        List<BikeDTO> bikes = FeignUtils.safeFeignCall(() -> bikeFeignClientV2.getBikesByUserId(userId)
                .stream()
                .map(bikeMapper::bikeFeignRestDtoV2_2_BikeDTO)
                .collect(Collectors.toList()));
        //@formatter:off
		return Map.of(
			"user", userMapper.user_2_UserDto(user), 
			"cars", cars, 
			"bikes", bikes
		);
		//@formatter:on
    }
}
