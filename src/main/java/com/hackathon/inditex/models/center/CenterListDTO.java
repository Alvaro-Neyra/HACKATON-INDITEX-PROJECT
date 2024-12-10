package com.hackathon.inditex.models.center;

import com.hackathon.inditex.Entities.Coordinates;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CenterListDTO {
    private Long id;
    private String name;
    private String capacity;
    private String status;
    private Integer currentLoad;
    private Integer maxCapacity;
    private Coordinates coordinates;

    public CenterListDTO(Long id, String name, String capacity, String status, Integer currentLoad, Integer maxCapacity, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.status = status;
        this.currentLoad = currentLoad;
        this.maxCapacity = maxCapacity;
        this.coordinates = coordinates;
    }
}
