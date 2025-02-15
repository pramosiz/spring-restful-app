package com.tutorial.bikeservice.application.serviceimpl;

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

import com.tutorial.bikeservice.application.services.BikeService;
import com.tutorial.bikeservice.domain.V2.dto.BikeDtoV2;
import com.tutorial.bikeservice.domain.V2.dto.NewBikeDtoV2;
import com.tutorial.bikeservice.domain.V2.model.BikeV2;
import com.tutorial.bikeservice.ports.outputs.feign.clients.UserFeignPortV2;
import com.tutorial.bikeservice.ports.outputs.feign.dto.UserFeignDtoV2;
import com.tutorial.bikeservice.ports.outputs.persistence.repositories.BikeRepositoryPort;

@SpringBootTest(classes = ApplicationTestServiceImplV2.class)
class BikeServiceImplTest {

    private final BikeService bikeService;

    public BikeServiceImplTest(@Autowired BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @MockBean
    BikeRepositoryPort bikeRepository;

    @MockBean
    UserFeignPortV2 userFeignClient;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<BikeV2> bikeCaptor;

    @Test
    void testGetAll_ReturnsListOfBikes() {

        // Given
        List<BikeV2> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Honda";
        String model1 = "CBR";
        Long userId = 1L;
        expectedResponse.add(BikeV2.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Yamaha";
        String model2 = "R1";
        Long userId2 = 2L;
        expectedResponse.add(BikeV2.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(bikeRepository.findAll()).thenReturn(expectedResponse);

        // When
        List<BikeDtoV2> response = bikeService.getAll();

        // Then
        verify(bikeRepository).findAll();
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
        List<BikeV2> bikes = new ArrayList<>();
        when(bikeRepository.findAll()).thenReturn(bikes);

        // When
        List<BikeDtoV2> response = bikeService.getAll();

        // Then
        verify(bikeRepository).findAll();
        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testGetById_ReturnExistingBike() {

        // Given
        Long id = 1L;
        String brand = "Honda";
        String model = "CBR";
        Long userId = 1L;
        when(bikeRepository.findById(id))
                .thenReturn(Optional.of(BikeV2.builder().id(id).brand(brand).model(model).userId(userId).build()));

        // When
        Optional<BikeDtoV2> response = bikeService.getById(id);

        // Then
        verify(bikeRepository).findById(idCaptor.capture());
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
        when(bikeRepository.findById(id)).thenReturn(Optional.empty());

        // When
        Optional<BikeDtoV2> response = bikeService.getById(id);

        // Then
        verify(bikeRepository).findById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        assertEquals(Optional.empty(), response);
    }

    @Test
    void testSaveNewBike_ReturnsBike() {

        // Given
        Long id = 1L;
        String brand = "Honda";
        String model = "CBR";
        Long userId = 1L;
        BikeV2 bikeSaved = BikeV2.builder().id(id).brand(brand).model(model).userId(userId).build();
        NewBikeDtoV2 newBikeDto = NewBikeDtoV2.builder().brand(brand).model(model).userId(userId).build();
        UserFeignDtoV2 userDto = UserFeignDtoV2.builder().id(userId).build();

        when(userFeignClient.getById(userId)).thenReturn(Optional.of(userDto));
        when(bikeRepository.save(any(BikeV2.class))).thenReturn(bikeSaved);

        // When
        Optional<BikeDtoV2> response = bikeService.saveNewBikeWithExternalCheck(newBikeDto);

        // Then
        verify(userFeignClient).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        verify(bikeRepository).save(bikeCaptor.capture());
        assertNotNull(bikeCaptor.getValue());
        assertEquals(brand, bikeCaptor.getValue().getBrand());
        assertEquals(model, bikeCaptor.getValue().getModel());
        assertEquals(userId, bikeCaptor.getValue().getUserId());

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
    void testSaveNewBike_ReturnsEmpty() {

        // Given
        String brand = "Honda";
        String model = "CBR";
        Long userId = 1L;
        NewBikeDtoV2 newBikeDto = NewBikeDtoV2.builder().brand(brand).model(model).userId(userId).build();
        when(userFeignClient.getById(userId)).thenReturn(Optional.empty());

        // When
        Optional<BikeDtoV2> response = bikeService.saveNewBikeWithExternalCheck(newBikeDto);

        // Then
        verify(userFeignClient).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        verify(bikeRepository, never()).save(bikeCaptor.capture());
        assertNotNull(response);
        assertEquals(Optional.empty(), response);
    }

    @Test
    void testGetByUserId_ReturnsListOfBikes() {

        // Given
        List<BikeV2> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Honda";
        String model1 = "CBR";
        Long userId = 1L;
        expectedResponse.add(BikeV2.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Yamaha";
        String model2 = "R1";
        Long userId2 = userId;
        expectedResponse.add(BikeV2.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(bikeRepository.findByUserId(userId)).thenReturn(expectedResponse);

        // When
        List<BikeDtoV2> response = bikeService.getByUserId(userId);

        // Then
        verify(bikeRepository).findByUserId(idCaptor.capture());
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
        List<BikeV2> bikes = new ArrayList<>();
        when(bikeRepository.findByUserId(userId)).thenReturn(bikes);

        // When
        List<BikeDtoV2> response = bikeService.getByUserId(userId);

        // Then
        verify(bikeRepository).findByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

        assertNotNull(response);
        assertEquals(0, response.size());
    }

    @Test
    void testDeleteById() {

        // Given
        Long id = 1L;
        doNothing().when(bikeRepository).deleteById(id);

        // When
        bikeService.deleteById(id);

        // Then
        verify(bikeRepository).deleteById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
    }

    @Test
    void testDeleteByUserId() {

        // Given
        Long userId = 1L;
        doNothing().when(bikeRepository).deleteByUserId(userId);

        // When
        bikeService.deleteByUserId(userId);

        // Then
        verify(bikeRepository).deleteByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());
    }
}
