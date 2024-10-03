package com.tutorial.userservice.serviceimpl;

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

import com.tutorial.userservice.repository.entities.User;
import com.tutorial.userservice.repository.repositories.UserRepository;
import com.tutorial.userservice.service.UserService;
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

    @Captor
    private ArgumentCaptor<User> userCaptor;
    @Captor
    private ArgumentCaptor<Long> idCaptor;

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
}
