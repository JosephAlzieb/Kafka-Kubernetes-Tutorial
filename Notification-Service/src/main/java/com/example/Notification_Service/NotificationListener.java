package com.example.Notification_Service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    @KafkaListener(topics = "orders", groupId = "notification-service")
    public void listen(String message) {
        System.out.println("Sending email confirmation for order: " + message);
    }
}
