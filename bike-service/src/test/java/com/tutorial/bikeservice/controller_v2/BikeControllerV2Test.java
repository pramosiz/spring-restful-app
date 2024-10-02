package com.tutorial.bikeservice.controller_v2;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.tutorial.bikeservice.service.BikeService;
import com.tutorial.bikeservice.service.dto.BikeDTO;
import com.tutorial.bikeservice.service.dto.NewBikeDTO;

import lombok.SneakyThrows;

@SpringBootTest(classes = ApplicationTestControllerV2.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class BikeControllerV2Test {

    final MockMvc httpClient;

    BikeControllerV2Test(@Autowired MockMvc httpClient) {
        this.httpClient = httpClient;
    }

    @MockBean
    BikeService bikeService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<NewBikeDTO> newBikeCaptor;

    @Test
    @SneakyThrows
    void testGetAll_ReturnsListOfBikes() {

        // Given
        List<BikeDTO> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Honda";
        String model1 = "CBR";
        Long userId = 1L;
        expectedResponse.add(BikeDTO.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Yamaha";
        String model2 = "R1";
        Long userId2 = 2L;
        expectedResponse.add(BikeDTO.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(bikeService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/bike"));

        // Then
        verify(bikeService).getAll();
        response.andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        assertNotNull(response.andReturn().getResponse().getContentAsString());
        response.andExpect(jsonPath("$[0].id").value(id1));
        response.andExpect(jsonPath("$[0].brand").value(brand1));
        response.andExpect(jsonPath("$[0].model").value(model1));
        response.andExpect(jsonPath("$[0].user_id").value(userId));
        response.andExpect(jsonPath("$[1].id").value(id2));
        response.andExpect(jsonPath("$[1].brand").value(brand2));
        response.andExpect(jsonPath("$[1].model").value(model2));
        response.andExpect(jsonPath("$[1].user_id").value(userId2));
    }

    @SneakyThrows
    void testGetAll_ReturnsEmptyList() {

        // Given
        List<BikeDTO> expectedResponse = new ArrayList<>();
        when(bikeService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/bike"));

        // Then
        verify(bikeService).getAll();
        response.andExpect(status().isOk()).andExpect(content().json("[]"));
        assertNotNull(response.andReturn().getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testGetById_ReturnExistingBike() {

        // Given
        Long id = 1L;
        String brand = "Honda";
        String model = "CBR";
        Long userId = 1L;
        BikeDTO bikeReturned = BikeDTO.builder().id(id).brand(brand).model(model).userId(userId).build();
        when(bikeService.getById(id)).thenReturn(Optional.of(bikeReturned));

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/bike/" + id));

        // Then
        verify(bikeService).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        response.andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        response.andExpect(jsonPath("$.id").value(id));
        response.andExpect(jsonPath("$.brand").value(brand));
        response.andExpect(jsonPath("$.model").value(model));
        response.andExpect(jsonPath("$.user_id").value(userId));
    }

    @Test
    @SneakyThrows
    void testGetById_ReturnNonExistingBike() {

        // Given
        Long id = 10L;

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/bike/" + id));

        // Then
        verify(bikeService).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        response.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testSaveNewUser_ReturnsBike() {

        // Given
        Long id = 1L;
        String brand = "Honda";
        String model = "CBR";
        Long userId = 1L;
        BikeDTO bikeReturned = BikeDTO.builder().id(id).brand(brand).model(model).userId(userId).build();
        when(bikeService.saveNewBike(any(NewBikeDTO.class))).thenReturn(bikeReturned);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.post("/api/v2/bike")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"brand\":\"Honda\",\"model\":\"CBR\",\"user_id\":1}"));

        // Then
        verify(bikeService).saveNewBike(newBikeCaptor.capture());
        assertNotNull(newBikeCaptor.getValue());
        assertEquals(brand, newBikeCaptor.getValue().getBrand());
        assertEquals(model, newBikeCaptor.getValue().getModel());
        assertEquals(userId, newBikeCaptor.getValue().getUserId());

        response.andExpect(status().isCreated());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        response.andExpect(jsonPath("$.id").value(id));
        response.andExpect(jsonPath("$.brand").value(brand));
        response.andExpect(jsonPath("$.model").value(model));
        response.andExpect(jsonPath("$.user_id").value(userId));
    }
}
