package com.tutorial.carservice.listener_v2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import com.tutorial.carservice.service.CarService;

@SpringBootTest(classes = ApplicationTestListenerV2.class)
// @Import(RabbitMQTestConfig.class)
public class NotifyDeleteListenerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitListenerTestHarness harness;

    @MockBean
    private CarService carService;

    @Captor
    private ArgumentCaptor<Long> userIdCaptor;

    @Test
    void testhandleMessage_NoError_IntegrationTest() {
        // Given
        NotifyDeleteListener listener = harness.getSpy("handle_message");
        Long userId = 10L;
        String message = "{\"user_id\":" + userId + "}";
        doNothing().when(carService).deleteByUserId(userId);
        // When
        this.rabbitTemplate.convertSendAndReceive("car_suscriber", message);
        // Then
        assertNotNull(listener);
        verify(carService).deleteByUserId(userIdCaptor.capture());
        assertNotNull(userIdCaptor.getValue());
        assertEquals(userId, userIdCaptor.getValue());
    }
}
