package com.tutorial.bikeservice.ports.inputs.amqp.listener_v2.listener;

public interface NotifyDeleteListenerPort {

    void handleMessage(String notificationMessage);
}
