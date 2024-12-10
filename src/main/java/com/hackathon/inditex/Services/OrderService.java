package com.hackathon.inditex.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.Repositories.OrderRepository;
import com.hackathon.inditex.models.order.OrderDTO;
import com.hackathon.inditex.models.order.OrderListDTO;
import com.hackathon.inditex.models.order.ResponseOrderDTO;
import com.hackathon.inditex.utils.Haversine;

import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CenterRepository centerRepository;

    @Transactional
    public ResponseOrderDTO createOrder(OrderDTO order) throws Exception{
        try {
            List<Order> orders = orderRepository.findOrderByCoordinates(order.getCoordinates().getLatitude(), order.getCoordinates().getLongitude());
            if (orders.size() > 0) {
                throw new Exception("There is already an order in that position.");
            }
            Order newOrder = new Order();
            newOrder.setCustomerId(order.getCustomerId());
            if (order.getSize().matches("S|M|B")) {
                newOrder.setSize(order.getSize());
            } else {
                throw new Exception("Invalid size. Size must be S, M or L.");
            }
            newOrder.setStatus("PENDING");
            newOrder.setAssignedCenter(null);
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(order.getCoordinates().getLatitude());
            coordinates.setLongitude(order.getCoordinates().getLongitude());
            newOrder.setCoordinates(coordinates);
            orderRepository.save(newOrder);
            return new ResponseOrderDTO(newOrder.getId(), newOrder.getCustomerId(), newOrder.getSize(), newOrder.getAssignedCenter(), newOrder.getCoordinates(), newOrder.getStatus(), "Order created successfully in PENDING status.");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<OrderListDTO> getOrders() {
        return orderRepository.listOrders();
    }

    @Transactional
    public Map<String, Object> assignOrders() {
        List<Order> orders = orderRepository.findOrderByStatus("PENDING");
        List<Center> centers = centerRepository.findCenterByStatus("AVAILABLE");

        if (orders.isEmpty()) {
            throw new IllegalStateException("No orders with status PENDING found.");
        }
        if (centers.isEmpty()) {
            throw new IllegalStateException("No centers with status AVAILABLE found.");
        }

        List<Map<String, Object>> processedOrders = new ArrayList<>();

        for (Order order : orders) {
            Center bestCenter = null;
            Double minDistance = Double.MAX_VALUE;

            for (Center center : centers) {
                if (center.getCapacity().equals(order.getSize()) && center.getCurrentLoad() < center.getMaxCapacity()) {
                    Double distance = Haversine.calculateDistance(
                        order.getCoordinates().getLatitude(),
                        order.getCoordinates().getLongitude(),
                        center.getCoordinates().getLatitude(),
                        center.getCoordinates().getLongitude()
                    );

                    if (distance < minDistance) {
                        minDistance = distance;
                        bestCenter = center;
                    }
                }
            }

            Map<String, Object> response = new HashMap<>();

            if (bestCenter != null) {
                order.setStatus("ASSIGNED");
                order.setAssignedCenter(bestCenter.getName());
                bestCenter.setCurrentLoad(bestCenter.getCurrentLoad() + 1);
                response.put("distance", minDistance);
                response.put("orderId", order.getId());
                response.put("assignedLogisticsCenter", bestCenter.getName());
                response.put("status", "ASSIGNED");
            } else {
                response.put("distance", null);
                response.put("orderId", order.getId());
                response.put("assignedLogisticsCenter", null);
                response.put("message", "All centers are at maximum capacity.");
                response.put("status", "PENDING");
            }

            processedOrders.add(response);
            if (bestCenter != null) {
                centerRepository.save(bestCenter);
            }
            if (order != null) {
                orderRepository.save(order);
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("processed-orders", processedOrders);
        return result;
    }
}
