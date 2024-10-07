package com.tutorial.carservice.serviceimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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

import com.tutorial.carservice.repository.entities.Car;
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
        String brand2 = "Yamaha";
        String model2 = "R1";
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
    void testSaveNewCar() {

        // // Given
        // Long id = 1L;
        // String brand = "Honda";
        // String model = "CBR";
        // Long userId = 1L;
        // Car carSaved =
        // Car.builder().id(id).brand(brand).model(model).userId(userId).build();
        // NewCarDTO newCarDto =
        // NewCarDTO.builder().brand(brand).model(model).userId(userId).build();

        // when(carRepository.save(any(Car.class))).thenReturn(carSaved);

        // // When
        // CarDTO response = carService.saveNewCar(newCarDto);

        // // Then
        // verify(carRepository).save(carCaptor.capture());
        // assertNotNull(carCaptor.getValue());
        // assertEquals(brand, carCaptor.getValue().getBrand());
        // assertEquals(model, carCaptor.getValue().getModel());
        // assertEquals(userId, carCaptor.getValue().getUserId());

        // assertNotNull(response);
        // assertNotNull(response.getId());
        // assertEquals(id, response.getId());
        // assertNotNull(response.getBrand());
        // assertEquals(brand, response.getBrand());
        // assertNotNull(response.getModel());
        // assertEquals(model, response.getModel());
        // assertNotNull(response.getUserId());
        // assertEquals(userId, response.getUserId());
    }
}
