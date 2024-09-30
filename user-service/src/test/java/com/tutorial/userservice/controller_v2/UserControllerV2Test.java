package com.tutorial.userservice.controller_v2;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
}
