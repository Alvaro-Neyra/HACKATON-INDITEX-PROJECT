package com.hackathon.inditex.Repositories;

import org.springframework.data.jpa.repository.Query;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hackathon.inditex.Entities.Order;
import com.hackathon.inditex.models.order.OrderListDTO;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.size = :size")
    public void findOrderBySize(@Param("size") String size);
    @Query("SELECT o FROM Order o WHERE o.coordinates.latitude = :latitude AND o.coordinates.longitude = :longitude")
    public List<Order> findOrderByCoordinates(@Param("latitude") Double latitude, @Param("longitude") Double longitude);
    @Query("SELECT new com.hackathon.inditex.models.order.OrderListDTO(o.id, o.customerId, o.size, o.status, o.assignedCenter, o.coordinates) FROM Order o")
    public List<OrderListDTO> listOrders();
    @Query("SELECT o FROM Order o WHERE o.status = :status")
    public List<Order> findOrderByStatus(@Param("status") String status);
}
