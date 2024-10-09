package com.tutorial.userservice.controller_v2;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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

import com.tutorial.userservice.service.UserService;
import com.tutorial.userservice.service.dto.BikeDTO;
import com.tutorial.userservice.service.dto.CarDTO;
import com.tutorial.userservice.service.dto.NewUserDTO;
import com.tutorial.userservice.service.dto.UserDTO;

import lombok.SneakyThrows;

@SpringBootTest(classes = ApplicationTestControllerV2.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc // Pedimos a Spring un cliente HTTP para atacar al Tomcat que has levantado
class UserControllerV2Test {

    final MockMvc httpClient; // Cliente HTTP para atacar al Tomcat que levanta Spring

    public UserControllerV2Test(@Autowired MockMvc httpClient) {
        this.httpClient = httpClient;
    }

    @MockBean
    UserService userService;

    @Captor
    private ArgumentCaptor<NewUserDTO> newUserCaptor;

    @Captor
    private ArgumentCaptor<Long> idCaptor;

    @Test
    @SneakyThrows
    void testGetAll_ReturnsListOfUsers() {

        // Given
        List<UserDTO> expectedResponse = new ArrayList<>();
        Long id1 = 1L;
        String name1 = "John";
        String email1 = "john@gmail.com";
        expectedResponse.add(UserDTO.builder().id(id1).name(name1).email(email1).build());

        Long id2 = 2L;
        String name2 = "Jane";
        String email2 = "jane@gmail.com";
        expectedResponse.add(UserDTO.builder().id(id2).name(name2).email(email2).build());

        when(userService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user"));

        // Then
        verify(userService).getAll();
        response.andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        assertNotNull(response.andReturn().getResponse().getContentAsString());
        response.andExpect(jsonPath("$[0].id").value(id1));
        response.andExpect(jsonPath("$[0].name").value(name1));
        response.andExpect(jsonPath("$[0].email").value(email1));
        response.andExpect(jsonPath("$[1].id").value(id2));
        response.andExpect(jsonPath("$[1].name").value(name2));
        response.andExpect(jsonPath("$[1].email").value(email2));
    }

    @Test
    @SneakyThrows
    void testGetAll_ReturnsEmptyList() {

        // Given
        List<UserDTO> expectedResponse = new ArrayList<>();
        when(userService.getAll()).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user"));

        // Then
        verify(userService).getAll();
        response.andExpect(status().isOk()).andExpect(content().json("[]"));
        assertNotNull(response.andReturn().getResponse().getContentAsString());
    }

    @Test
    @SneakyThrows
    void testGetById_ReturnExistingUser() {

        // Given
        Long id = 1L;
        String name = "John";
        String email = "john@gmail.com";
        UserDTO userReturned = UserDTO.builder().id(id).name(name).email(email).build();
        when(userService.getById(id)).thenReturn(Optional.of(userReturned));

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + id));

        // Then
        verify(userService).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        assertNotNull(response);
        response.andExpect(status().isOk());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON)); // MimeType (Tipo de dato en la
                                                                               // respuesta)
        response.andExpect(jsonPath("$.id").value(id));
        response.andExpect(jsonPath("$.name").value(name));
        response.andExpect(jsonPath("$.email").value(email));
    }

    @Test
    @SneakyThrows
    void testGetById_ReturnNonExistingUser() {

        // Given
        Long id = 10L;

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + id));

        // Then
        verify(userService).getById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        response.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testSaveNewUser_ReturnsUser() {

        // Given
        Long id = 1L;
        String name = "John";
        String email = "john@gmail.com";

        UserDTO userReturned = UserDTO.builder().id(id).name(name).email(email).build();
        when(userService.saveNewUser(any(NewUserDTO.class))).thenReturn(userReturned);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.post("/api/v2/user")
                .contentType(MediaType.APPLICATION_JSON).content("{\"name\":\"John\",\"email\":\"john@gmail.com\"}"));

        // Then
        verify(userService).saveNewUser(newUserCaptor.capture());
        assertNotNull(newUserCaptor.getValue());
        assertEquals(name, newUserCaptor.getValue().getName());
        assertEquals(email, newUserCaptor.getValue().getEmail());

        response.andExpect(status().isCreated());
        response.andExpect(content().contentType(MediaType.APPLICATION_JSON));
        response.andExpect(jsonPath("$.id").value(id));
        response.andExpect(jsonPath("$.name").value(name));
        response.andExpect(jsonPath("$.email").value(email));
    }

    @Test
    @SneakyThrows
    void testGetCars_ReturnsListOfCars() {

        // Given
        List<CarDTO> expectedResponse = new ArrayList<>();

        Long id1 = 1L;
        String brand1 = "Toyota";
        String model1 = "Corolla";
        Long userId = 1L;
        expectedResponse.add(CarDTO.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Ford";
        String model2 = "Focus";
        Long userId2 = userId;
        expectedResponse.add(CarDTO.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(userService.getCarsByUserId(userId)).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + userId + "/cars"));

        // Then
        verify(userService).getCarsByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

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
    void testGetCars_ReturnsEmptyList() {

        // Given
        Long userId = 1L;
        List<CarDTO> expectedResponse = new ArrayList<>();
        when(userService.getCarsByUserId(userId)).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + userId + "/cars"));

        // Then
        verify(userService).getCarsByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());
        response.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testGetBikes_ReturnsListOfBikes() {

        // Given
        List<BikeDTO> expectedResponse = new ArrayList<>();

        Long id1 = 1L;
        String brand1 = "Orbea";
        String model1 = "Orca";
        Long userId = 1L;
        expectedResponse.add(BikeDTO.builder().id(id1).brand(brand1).model(model1).userId(userId).build());

        Long id2 = 2L;
        String brand2 = "Specialized";
        String model2 = "Stumpjumper";
        Long userId2 = userId;
        expectedResponse.add(BikeDTO.builder().id(id2).brand(brand2).model(model2).userId(userId2).build());

        when(userService.getBikesByUserId(userId)).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + userId + "/bikes"));

        // Then
        verify(userService).getBikesByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());

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
    void testGetBikes_ReturnsEmptyList() {

        // Given
        Long userId = 1L;
        List<BikeDTO> expectedResponse = new ArrayList<>();
        when(userService.getBikesByUserId(userId)).thenReturn(expectedResponse);

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.get("/api/v2/user/" + userId + "/bikes"));

        // Then
        verify(userService).getBikesByUserId(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(userId, idCaptor.getValue());
        response.andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    void testDeleteById() {

        // Given
        Long id = 1L;

        // When
        ResultActions response = httpClient.perform(MockMvcRequestBuilders.delete("/api/v2/user/" + id));

        // Then
        verify(userService).deleteById(idCaptor.capture());
        assertNotNull(idCaptor.getValue());
        assertEquals(id, idCaptor.getValue());
        response.andExpect(status().isNoContent());
    }
}
