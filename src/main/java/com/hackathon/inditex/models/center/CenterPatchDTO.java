package com.hackathon.inditex.models.center;

import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.models.coordinates.CoordinatesDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterPatchDTO {
    private String name;
    private String capacity;
    private String status;
    private Integer currentLoad;
    private Integer maxCapacity;
    private Coordinates coordinates;
}
