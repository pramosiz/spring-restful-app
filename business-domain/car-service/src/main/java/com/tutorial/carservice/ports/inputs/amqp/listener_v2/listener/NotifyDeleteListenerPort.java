package com.tutorial.carservice.ports.inputs.amqp.listener_v2.listener;

public interface NotifyDeleteListenerPort {

    void handleMessage(String notificationMessage);
}
