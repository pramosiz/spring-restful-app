package com.tutorial.carservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.tutorial.carservice.feignclient.clients.UserFeignClientV2;
import com.tutorial.carservice.feignclient.dto.UserRestDtoV2;
import com.tutorial.carservice.repository.domains.Car;
import com.tutorial.carservice.repository.repositories.CarRepository;
import com.tutorial.carservice.service.CarService;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

@SpringBootTest(classes = ApplicationTestServiceImplV2.class)
class CarServiceImplTest {

    private final CarService carService;

    public CarServiceImplTest(@Autowired CarService carService) {
        this.carService = carService;
    }

    @MockBean
    CarRepository carRepository;

    @MockBean
    UserFeignClientV2 userFeignClient;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<Car> carCaptor;

    @Test
    void testGetAll_ReturnsListOfCars() {

        // Given
        List<Car> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Seat";
        String model1 = "Ibiza";
        Long userId = 1L;
        expectedResponse.add(Car.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Tesla";
        String model2 = "S";
        Long userId2 = 2L;
        expectedResponse.add(Car.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(carRepository.findAll()).thenReturn(expectedResponse);

        // When
        List<CarDTO> response = carService.getAll();

        // Then
        verify(carRepository).findAll();
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
    void testGetAll_ReturnsEmptyList() {

        // Given
        List<Car> cars = new ArrayList<>();
        when(carRepository.findAll()).thenReturn(cars);

        // When
        List<CarDTO> response = carService.getAll();

        // Then
        verify(carRepository).findAll();
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testGetById_ReturnExistingCar() {

        // Given
        Long id = 1L;
        String brand = "Seat";
        String model = "Ibiza";
        Long userId = 1L;
        when(carRepository.findById(id))
                .thenReturn(Optional.of(Car.builder().id(id).brand(brand).model(model).userId(userId).build()));

        // When
        Optional<CarDTO> response = carService.getById(id);

        // Then
        verify(carRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        assertNotNull(response.get());
        assertNotNull(response.get().getId());
        assertEquals(id, response.get().getId());
        assertNotNull(response.get().getBrand());
        assertEquals(brand, response.get().getBrand());
        assertNotNull(response.get().getModel());
        assertEquals(model, response.get().getModel());
        assertNotNull(response.get().getUserId());
        assertEquals(userId, response.get().getUserId());
    }

    @Test
    void testGetById_ReturnEmptyOptional() {

        // Given
        Long id = 1L;
        when(carRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<CarDTO> response = carService.getById(id);

        // Then
        verify(carRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        assertEquals(Optional.empty(), response);
    }

    @Test
    void testSaveNewCar_ReturnsCar() {

        // Given
        Long id = 1L;
        String brand = "Tesla";
        String model = "S";
        Long userId = 1L;
        Car carSaved = Car.builder().id(id).brand(brand).model(model).userId(userId).build();
        NewCarDTO newCarDto = NewCarDTO.builder().brand(brand).model(model).userId(userId).build();
        UserRestDtoV2 userDto = UserRestDtoV2.builder().id(userId).build();
        when(userFeignClient.getById(userId)).thenReturn(Optional.of(userDto));
        when(carRepository.save(any(Car.class))).thenReturn(carSaved);

        // When
        Optional<CarDTO> response = carService.saveNewCarWithExternalCheck(newCarDto);

        // Then
        verify(userFeignClient).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        verify(carRepository).save(carCaptor.capture());
        assertNotNull(carCaptor.getValue());
        assertEquals(brand, carCaptor.getValue().getBrand());
        assertEquals(model, carCaptor.getValue().getModel());
        assertEquals(userId, carCaptor.getValue().getUserId());

        assertNotNull(response);
        assertNotNull(response.get().getId());
        assertEquals(id, response.get().getId());
        assertNotNull(response.get().getBrand());
        assertEquals(brand, response.get().getBrand());
        assertNotNull(response.get().getModel());
        assertEquals(model, response.get().getModel());
        assertNotNull(response.get().getUserId());
        assertEquals(userId, response.get().getUserId());
    }

    @Test
    void testSaveNewCar_ReturnsEmpty() {

        // Given
        String brand = "Tesla";
        String model = "S";
        Long userId = 1L;
        NewCarDTO newCarDto = NewCarDTO.builder().brand(brand).model(model).userId(userId).build();
        when(userFeignClient.getById(userId)).thenReturn(Optional.empty());

        // When
        Optional<CarDTO> response = carService.saveNewCarWithExternalCheck(newCarDto);

        // Then
        verify(userFeignClient).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        verify(carRepository, never()).save(carCaptor.capture());

        assertNotNull(response);
        assertEquals(Optional.empty(), response);
    }

    @Test
    void testGetByUserId_ReturnsListOfCars() {

        // Given
        List<Car> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Seat";
        String model1 = "Ibiza";
        Long userId = 1L;
        expectedResponse.add(Car.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Tesla";
        String model2 = "S";
        Long userId2 = userId;
        expectedResponse.add(Car.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(carRepository.findByUserId(userId)).thenReturn(expectedResponse);

        // When
        List<CarDTO> response = carService.getByUserId(userId);

        // Then
        verify(carRepository).findByUserId(idCaptor.capture());
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
    void testGetByUserId_ReturnsEmptyList() {

        // Given
        Long userId = 1L;
        List<Car> cars = new ArrayList<>();
        when(carRepository.findByUserId(userId)).thenReturn(cars);

        // When
        List<CarDTO> response = carService.getByUserId(userId);

        // Then
        verify(carRepository).findByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testDeleteById() {

        // Given
        Long id = 1L;
        doNothing().when(carRepository).deleteById(id);

        // When
        carRepository.deleteById(id);

        // Then
        verify(carRepository).deleteById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
    }

    @Test
    void testDeleteByUserId() {

        // Given
        Long userId = 1L;
        doNothing().when(carRepository).deleteByUserId(userId);

        // When
        carRepository.deleteByUserId(userId);

        // Then
        verify(carRepository).deleteByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());
    }
}
