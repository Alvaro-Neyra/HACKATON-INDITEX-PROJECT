package com.hackathon.inditex.models.order;

import com.hackathon.inditex.Entities.Coordinates;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderListDTO {
    private Long id;
    private Long customerId;
    private String size;
    private String status;
    private String assignedCenter;
    Coordinates coordinates;

    public OrderListDTO(Long id, Long customerId, String size, String status, String assignedCenter, Coordinates coordinates) {
        this.id = id;
        this.customerId = customerId;
        this.size = size;
        this.status = status;
        this.assignedCenter = assignedCenter;
        this.coordinates = coordinates;
    }
}
