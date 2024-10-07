package com.tutorial.carservice.controller_v2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.tutorial.carservice.service.CarService;
import com.tutorial.carservice.service.dto.CarDTO;
import com.tutorial.carservice.service.dto.NewCarDTO;

import lombok.SneakyThrows;
import reactor.core.publisher.Mono;

@SpringBootTest(classes = ApplicationTestControllerV2.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CarControllerV2Test {

    final MockMvc httpClient; // MockMvc is for block application

    final WebTestClient webClientTest; // WebTestClient is for reactive application

    CarControllerV2Test(@Autowired MockMvc httpClient, @Autowired WebTestClient webClientTest) {
        this.httpClient = httpClient;
        this.webClientTest = webClientTest;
    }

    @MockBean
    CarService carService;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Captor
    private ArgumentCaptor<NewCarDTO> newCarCaptor;

    @Test
    @SneakyThrows
    void testGetAll_ReturnsListOfCars() {

        // Given
        List<CarDTO> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String brand1 = "Seat";
        String model1 = "Ibiza";
        Long userId = 1L;
        expectedResponse.add(CarDTO.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Hyundai";
        String model2 = "i30";
        Long userId2 = 2L;
        expectedResponse.add(CarDTO.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(carService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/car"));

        // Then
        verify(carService).getAll();
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

    @Test
    @SneakyThrows
    void testGetAll_ReturnsEmptyList() {

        // Given
        List<CarDTO> expectedResponse = new ArrayList<>();
        when(carService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/car"));

        // Then
        verify(carService).getAll();
        response.andExpect(status().isOk()).andExpect(content().json("[]"));
        assertNotNull(response.andReturn().getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testGetById_ReturnExistingCar() {

        // Given
        Long id = 1L;
        String brand = "Seat";
        String model = "Ibiza";
        Long userId = 1L;
        CarDTO carReturned = CarDTO.builder().id(id).brand(brand).model(model).userId(userId).build();
        when(carService.getById(id)).thenReturn(Optional.of(carReturned));

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/car/" + id));

        // Then
        verify(carService).getById(idCaptor.capture());
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
    void testGetById_ReturnNonExistingCar() {

        // Given
        Long id = 10L;

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/car/" + id));

        // Then
        verify(carService).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        response.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testSaveNewCar_ReturnsCar() {

        // Given
        Long id = 1L;
        String brand = "Seat";
        String model = "Ibiza";
        Long userId = 1L;
        CarDTO carReturned = CarDTO.builder().id(id).brand(brand).model(model).userId(userId).build();
        String contentTest = "{\"brand\":\"Seat\",\"model\":\"Ibiza\",\"user_id\":1}";
        when(carService.saveNewCarWithExternalCheck(any(NewCarDTO.class))).thenReturn(Mono.just(carReturned));

        // When
        //@formatter:off
        webClientTest.post()
                .uri("/api/v2/car")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contentTest)
                .exchange()
                // Then
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id)
                .jsonPath("$.brand").isEqualTo(brand)
                .jsonPath("$.model").isEqualTo(model)
                .jsonPath("$.user_id").isEqualTo(userId);
        //@formatter:on

        verify(carService).saveNewCarWithExternalCheck(newCarCaptor.capture());
        assertNotNull(newCarCaptor.getValue());
        assertEquals(brand, newCarCaptor.getValue().getBrand());
        assertEquals(model, newCarCaptor.getValue().getModel());
        assertEquals(userId, newCarCaptor.getValue().getUserId());
    }

    @Test
    @SneakyThrows
    void testSaveNewCar_ReturnsBadRequest() {

        // Given
        String brand = "Seat";
        String model = "Ibiza";
        Long userId = 1L;
        NewCarDTO newCarDTO = NewCarDTO.builder().brand(brand).model(model).userId(userId).build();
        String contentTest = "{\"brand\":\"Seat\",\"model\":\"Ibiza\",\"user_id\":1}";
        when(carService.saveNewCarWithExternalCheck(newCarDTO)).thenReturn(Mono.empty());

        // When
        //@formatter:off
        webClientTest.post()
                .uri("/api/v2/car")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(contentTest)
                .exchange()
                // Then
                .expectStatus().isBadRequest();
        //@formatter:on

        verify(carService).saveNewCarWithExternalCheck(newCarCaptor.capture());
        assertNotNull(newCarCaptor.getValue());
    }
}
