package com.example.Notification_Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationListener {

    private Logger logger = LoggerFactory.getLogger(NotificationListener.class);


    @KafkaListener(topics = "orders", groupId = "notification-service")
    public void listen(String message) {

        logger.info("Received order message: " + message);
    }
}
