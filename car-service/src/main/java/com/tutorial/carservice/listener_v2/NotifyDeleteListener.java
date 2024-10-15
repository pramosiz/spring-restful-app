package com.tutorial.carservice.listener_v2;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorial.carservice.amqp.dto.NotificationDTO;
import com.tutorial.carservice.service.CarService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class NotifyDeleteListener {

    private final CarService carService;
    private final ObjectMapper objectMapper;

    @RabbitListener(id = "handle_message", queues = "#{carSuscriberQueue.name}")
    public void handleMessage(String notificationMessage) {
        try {
            NotificationDTO notificationDTO = objectMapper.readValue(notificationMessage,
                    NotificationDTO.class);
            carService.deleteByUserId(notificationDTO.getUserId());
            log.info("Deleted cars for user ID: {}", notificationDTO.getUserId());
        } catch (Exception e) {
            log.warn("Failed to process delete notification", e);
        }
    }
}
