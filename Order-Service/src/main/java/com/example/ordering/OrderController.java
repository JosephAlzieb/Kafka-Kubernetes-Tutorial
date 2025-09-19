package com.example.ordering;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final List<Order> orders = new ArrayList<>();
    private final KafkaTemplate<String, String> kafkaTemplate;
    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orders.add(order);
        kafkaTemplate.send("orders", order.id() + ":" + order.item());

        logger.info("Order created: " + order.id());
        logger.info("Topic" + kafkaTemplate.getDefaultTopic());
        logger.info("Metrics" + kafkaTemplate.metrics());
        logger.info(kafkaTemplate.toString());

        return "Order created: " + order.id();
    }

    @GetMapping
    public List<Order> listOrders() {
        return orders;
    }
}
