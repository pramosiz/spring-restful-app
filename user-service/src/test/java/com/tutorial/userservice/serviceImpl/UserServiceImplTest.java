package com.tutorial.userservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tutorial.userservice.feignclient.clients.BikeFeignClientV2;
import com.tutorial.userservice.feignclient.clients.CarFeignClientV2;
import com.tutorial.userservice.feignclient.dto.BikeFeignRestDtoV2;
import com.tutorial.userservice.feignclient.dto.CarFeignRestDtoV2;
import com.tutorial.userservice.repository.entities.User;
import com.tutorial.userservice.repository.repositories.UserRepository;
import com.tutorial.userservice.service.UserService;
import com.tutorial.userservice.service.dto.BikeDTO;
import com.tutorial.userservice.service.dto.CarDTO;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

@SpringBootTest(classes = ApplicationTestServiceImplV2.class)
class UserServiceImplTest {

    private final UserService userService;

    public UserServiceImplTest(@Autowired UserService userService) {
        this.userService = userService;
    }

    @MockBean
    UserRepository userRepository;

    @MockBean
    CarFeignClientV2 carFeignClientV2;

    @MockBean
    BikeFeignClientV2 bikeFeignClientV2;

    @MockBean
    RabbitTemplate rabbitTemplate;

    @MockBean
    FanoutExchange notifyDeleteInfoFanout;

    @Captor
    private ArgumentCaptor<User> userCaptor;
    @Captor
    private ArgumentCaptor<Long> idCaptor;
    @Captor
    private ArgumentCaptor<String> fanoutCaptor;

    @Test
    void testGetAll_ReturnsListOfUsers() {

        // Given
        List<User> users = new ArrayList<>();
        Long id1 = 1L;
        String name1 = "John";
        String email1 = "john@gmail.com";
        users.add(User.builder().id(id1).name(name1).email(email1).build());

        Long id2 = 2L;
        String name2 = "Jane";
        String email2 = "jane@gmail.com";
        users.add(User.builder().id(id2).name(name2).email(email2).build());

        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserDTO> response = userService.getAll();

        // Then
        verify(userRepository).findAll(); // Verifica que se ha llamado al método findAll() del userRepository
        assertNotNull(response);
        assertNotNull(response.get(0));
        assertNotNull(response.get(0).getId());
        assertEquals(id1, response.get(0).getId());
        assertNotNull(response.get(0).getName());
        assertEquals(name1, response.get(0).getName());
        assertNotNull(response.get(0).getEmail());
        assertEquals(email1, response.get(0).getEmail());

        assertNotNull(response.get(1));
        assertNotNull(response.get(1).getId());
        assertEquals(id2, response.get(1).getId());
        assertNotNull(response.get(1).getName());
        assertEquals(name2, response.get(1).getName());
        assertNotNull(response.get(1).getEmail());
        assertEquals(email2, response.get(1).getEmail());
    }

    @Test
    void testGetAll_ReturnsEmptyList() {

        // Given
        List<User> users = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(users);

        // When
        List<UserDTO> response = userService.getAll();

        // Then
        verify(userRepository).findAll();
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testGetById_ReturnExistingUser() {

        // Given
        Long id = 1L;
        String name = "John";
        String email = "john@gmail.com";
        User user = User.builder().id(id).name(name).email(email).build();
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        Optional<UserDTO> response = userService.getById(id);

        // Then
        verify(userRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        assertNotNull(response.get());
        assertNotNull(response.get().getId());
        assertEquals(id, response.get().getId());
        assertNotNull(response.get().getName());
        assertEquals(name, response.get().getName());
        assertNotNull(response.get().getEmail());
        assertEquals(email, response.get().getEmail());
    }

    @Test
    void testGetById_ReturnEmptyOptional() {

        // Given
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<UserDTO> response = userService.getById(id);

        // Then
        verify(userRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        assertEquals(Optional.empty(), response);
    }

    @Test
    void testSaveNewUser() {

        // Given
        Long id = 1L;
        String name = "John";
        String email = "john@gmail.com";
        User userSaved = User.builder().id(id).name(name).email(email).build();
        NewUserDTO newUserDto = NewUserDTO.builder().name(name).email(email).build();
        when(userRepository.save(any(User.class))).thenReturn(userSaved);

        // When
        UserDTO response = userService.saveNewUser(newUserDto);

        // Then
        verify(userRepository).save(userCaptor.capture());
        assertNotNull(userCaptor.getValue());
        assertEquals(name, userCaptor.getValue().getName());
        assertEquals(email, userCaptor.getValue().getEmail());

        assertNotNull(response);
        assertNotNull(response.getId());
        assertEquals(id, response.getId());
        assertNotNull(response.getName());
        assertEquals(name, response.getName());
        assertNotNull(response.getEmail());
        assertEquals(email, response.getEmail());
    }

    @Test
    void testGetCarsByUserId_ReturnsListOfCars() {

        // Given
        List<CarFeignRestDtoV2> cars = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Toyota";
        String model1 = "Corolla";
        Long userId = 1L;
        cars.add(CarFeignRestDtoV2.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Ford";
        String model2 = "Focus";
        Long userId2 = userId;
        cars.add(CarFeignRestDtoV2.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(carFeignClientV2.getCarsByUserId(userId)).thenReturn(cars);

        // When
        List<CarDTO> response = userService.getCarsByUserId(userId);

        // Then
        verify(carFeignClientV2).getCarsByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertNotNull(response.get(0));
        assertNotNull(response.get(0).getId());
        assertEquals(id1, response.get(0).getId());
        assertNotNull(response.get(0).getBrand());
        assertEquals(brand1, response.get(0).getBrand());
        assertNotNull(response.get(0).getModel());
        assertEquals(model1, response.get(0).getModel());
        assertNotNull(response.get(0).getUserId());
        assertEquals(userId, response.get(0).getUserId());

        assertNotNull(response.get(1));
        assertNotNull(response.get(1).getId());
        assertEquals(id2, response.get(1).getId());
        assertNotNull(response.get(1).getBrand());
        assertEquals(brand2, response.get(1).getBrand());
        assertNotNull(response.get(1).getModel());
        assertEquals(model2, response.get(1).getModel());
        assertNotNull(response.get(1).getUserId());
        assertEquals(userId2, response.get(1).getUserId());
    }

    @Test
    void testGetCarsByUserId_ReturnsEmptyList() {

        // Given
        List<CarFeignRestDtoV2> cars = new ArrayList<>();
        Long userId = 1L;
        when(carFeignClientV2.getCarsByUserId(userId)).thenReturn(cars);

        // When
        List<CarDTO> response = userService.getCarsByUserId(userId);

        // Then
        verify(carFeignClientV2).getCarsByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testGetBikesByUserId_ReturnsListOfBikes() {

        // Given
        List<BikeFeignRestDtoV2> bikes = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Honda";
        String model1 = "CBR";
        Long userId = 1L;
        bikes.add(BikeFeignRestDtoV2.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Yamaha";
        String model2 = "R1";
        Long userId2 = userId;
        bikes.add(BikeFeignRestDtoV2.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(bikeFeignClientV2.getBikesByUserId(userId)).thenReturn(bikes);

        // When
        List<BikeDTO> response = userService.getBikesByUserId(userId);

        // Then
        verify(bikeFeignClientV2).getBikesByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertNotNull(response.get(0));
        assertNotNull(response.get(0).getId());
        assertEquals(id1, response.get(0).getId());
        assertNotNull(response.get(0).getBrand());
        assertEquals(brand1, response.get(0).getBrand());
        assertNotNull(response.get(0).getModel());
        assertEquals(model1, response.get(0).getModel());
        assertNotNull(response.get(0).getUserId());
        assertEquals(userId, response.get(0).getUserId());

        assertNotNull(response.get(1));
        assertNotNull(response.get(1).getId());
        assertEquals(id2, response.get(1).getId());
        assertNotNull(response.get(1).getBrand());
        assertEquals(brand2, response.get(1).getBrand());
        assertNotNull(response.get(1).getModel());
        assertEquals(model2, response.get(1).getModel());
        assertNotNull(response.get(1).getUserId());
        assertEquals(userId2, response.get(1).getUserId());
    }

    @Test
    void testGetBikesByUserId_ReturnsEmptyList() {

        // Given
        List<BikeFeignRestDtoV2> bikes = new ArrayList<>();
        Long userId = 1L;
        when(bikeFeignClientV2.getBikesByUserId(userId)).thenReturn(bikes);

        // When
        List<BikeDTO> response = userService.getBikesByUserId(userId);

        // Then
        verify(bikeFeignClientV2).getBikesByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testDeleteById() {

        // Given
        Long id = 1L;
        String fanoutName = "NOTIFY_DELETE_INFO";
        when(notifyDeleteInfoFanout.getName()).thenReturn(fanoutName);
        doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString(), anyLong());

        // When
        userService.deleteById(id);

        // Then
        verify(userRepository).deleteById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());

        verify(rabbitTemplate).convertAndSend(fanoutCaptor.capture(), anyString(), idCaptor.capture());
        assertNotNull(fanoutCaptor.getValue());
        assertEquals(fanoutName, fanoutCaptor.getValue());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
    }
}
