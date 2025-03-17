package com.tutorial.bikeservice.adapters.inputs.amqp.listener_v2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.bikeservice.application.services.BikeService;
import com.tutorial.bikeservice.ports.inputs.amqp.listener_v2.dto.NotificationDTO;
import com.tutorial.bikeservice.ports.inputs.amqp.listener_v2.listener.NotifyDeleteListenerPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotifyDeleteListener implements NotifyDeleteListenerPort {

    private final BikeService bikeService;
    private final ObjectMapper objectMapper;

    @RabbitListener(id = "handle_message", queues = "#{bikeSuscriberQueue.name}")
    public void handleMessage(String notificationMessage) {
        try {
            NotificationDTO notificationDTO = objectMapper.readValue(notificationMessage, NotificationDTO.class);
            bikeService.deleteByUserId(notificationDTO.getUserId());
            log.info("Deleted bikes for user ID: {}", notificationDTO.getUserId());
        } catch (Exception e) {
            log.warn("Failed to process delete notification", e);
        }
    }
}
