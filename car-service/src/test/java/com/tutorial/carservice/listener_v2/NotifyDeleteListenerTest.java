package com.tutorial.carservice.listener_v2;

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
import com.tutorial.carservice.service.CarService;

@SpringBootTest(classes = ApplicationTestListenerV2.class)
@Import(BeansConfigurationTest.class)
public class NotifyDeleteListenerTest {

    public NotifyDeleteListenerTest(@Autowired ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    private final ObjectMapper objectMapper;

    @MockBean
    private CarService carService;

    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    private NotifyDeleteListener notifyDeleteListener;

    @BeforeEach
    public void setUp() {
        notifyDeleteListener = new NotifyDeleteListener(carService, objectMapper);
    }

    @AfterEach
    void tearDown() {
        notifyDeleteListener = null;
    }

    @Test
    void testhandleMessage_NoError_IntegrationTest() {
        // Given
        Long userId = 10L;
        String message = "{\"user_id\":" + userId + "}";
        doNothing().when(carService).deleteByUserId(userId);
        // When
        notifyDeleteListener.handleMessage(message);
        // Then
        verify(carService).deleteByUserId(userIdCaptor.capture());
        assertNotNull(userIdCaptor.getValue());
        assertEquals(userId, userIdCaptor.getValue());
    }
}
