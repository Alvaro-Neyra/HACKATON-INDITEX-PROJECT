package com.hackathon.inditex.models.order;

import com.hackathon.inditex.models.coordinates.CoordinatesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long customerId;
    private String size;
    private CoordinatesDTO coordinates;
}
