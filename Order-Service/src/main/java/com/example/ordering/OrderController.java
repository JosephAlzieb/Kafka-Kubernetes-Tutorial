package com.example.ordering;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final List<Order> orders = new ArrayList<>();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public OrderController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public String createOrder(@RequestBody Order order) {
        orders.add(order);
        kafkaTemplate.send("orders", order.id() + ":" + order.item());
        return "Order created: " + order.id();
    }

    @GetMapping
    public List<Order> listOrders() {
        return orders;
    }
}
