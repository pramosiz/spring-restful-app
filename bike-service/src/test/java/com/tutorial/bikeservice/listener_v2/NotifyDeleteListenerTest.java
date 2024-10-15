package com.tutorial.bikeservice.listener_v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.bikeservice.service.BikeService;

@SpringBootTest(classes = ApplicationTestListenerV2.class)
@Import(BeansConfigurationTest.class)
public class NotifyDeleteListenerTest {

    public NotifyDeleteListenerTest(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    @MockBean
    private BikeService bikeService;

    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    private NotifyDeleteListener notifyDeleteListener;

    @BeforeEach
    public void setUp() {
        notifyDeleteListener = new NotifyDeleteListener(bikeService, objectMapper);
    }

    @AfterEach
    void tearDown() {
        notifyDeleteListener = null;
    }

    @Test
    void testHandleMessage() {
        // Given
        Long userId = 10L;
        String message = "{\"user_id\":" + userId + "}";
        doNothing().when(bikeService).deleteByUserId(userId);
        // When
        notifyDeleteListener.handleMessage(message);
        // Then
        verify(bikeService).deleteByUserId(userIdCaptor.capture());
        assertNotNull(userIdCaptor.getValue());
        assertEquals(userId, userIdCaptor.getValue());
    }
}
