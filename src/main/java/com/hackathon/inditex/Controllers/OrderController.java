package com.hackathon.inditex.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.inditex.Services.OrderService;
import com.hackathon.inditex.models.order.OrderDTO;
import com.hackathon.inditex.models.order.ResponseOrderDTO;

@RestController
@RequestMapping("/api")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDTO order) {
        try {
            ResponseOrderDTO respuesta = orderService.createOrder(order);
            return ResponseEntity.ok().body(respuesta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<Object> getOrders() {
        try {
            return ResponseEntity.ok().body(orderService.getOrders());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/orders/order-assignations")
    public ResponseEntity<Object> assignOrders() {
        try {
            Map<String, Object> orders = orderService.assignOrders();
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
