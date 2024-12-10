package com.hackathon.inditex.Services;

import java.util.List;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hackathon.inditex.Entities.Center;
import com.hackathon.inditex.Entities.Coordinates;
import com.hackathon.inditex.Repositories.CenterRepository;
import com.hackathon.inditex.models.center.CenterDTO;
import com.hackathon.inditex.models.center.CenterListDTO;
import com.hackathon.inditex.models.center.CenterPatchDTO;

import org.springframework.transaction.annotation.Transactional;

@Service
public class CenterService {
    @Autowired
    private CenterRepository centerRepository;

    @Transactional
    public void createCenter(CenterDTO centerDTO) throws Exception{
        try {
            if (centerDTO.getCurrentLoad() >= centerDTO.getMaxCapacity()) {
                throw new Exception("Current load cannot exceed max capacity.");
            }
            // Verify if there's a center with the same coordinates
            List<Center> centers = centerRepository.findCenterByCoordinates(centerDTO.getCoordinates().getLatitude(), centerDTO.getCoordinates().getLongitude());
            if (centers.size() > 0) {
                throw new Exception("There is already a logistics center in that position.");
            }
            Center center = new Center();
            center.setName(centerDTO.getName());
            // The centers can support 3 types of orders: B (Big), M (Medium), S (Small). Also a combination of two or all. For example, a centre can be MS for medium and small orders.
            if (centerDTO.getCapacity().matches("[BMS]*")) {
                center.setCapacity(centerDTO.getCapacity());
            } else {
                throw new Exception("Invalid status. The centers can support 3 types of orders: B (Big), M (Medium), S (Small). Also a combination of two or all. For example, a centre can be MS for medium and small orders.");
            }
            center.setStatus(centerDTO.getStatus());
            center.setCurrentLoad(centerDTO.getCurrentLoad());
            center.setMaxCapacity(centerDTO.getMaxCapacity());
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(centerDTO.getCoordinates().getLatitude());
            coordinates.setLongitude(centerDTO.getCoordinates().getLongitude());
            center.setCoordinates(coordinates);
            centerRepository.save(center);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<CenterListDTO> getCenters() {
        return centerRepository.listCenters();
    }

    @Transactional(readOnly = true)
    public CenterListDTO getCenterById(Long id) {
        return centerRepository.getCenterById(id);
    }

    @Transactional
    public void updateCenter(Long id, CenterPatchDTO centerPatchDTO) throws Exception {
        try {
            Center center = centerRepository.findById(id).orElseThrow(() -> new Exception("Center not found"));

            if (centerPatchDTO.getCurrentLoad() != null) {
                if (centerPatchDTO.getCurrentLoad() > center.getMaxCapacity()) {
                    throw new Exception("Current load cannot exceed max capacity.");
                }
            }

            if (centerPatchDTO.getName() != null) {
                center.setName(centerPatchDTO.getName());
            }
            if (centerPatchDTO.getCapacity() != null) {
                center.setCapacity(centerPatchDTO.getCapacity());
            }
            if (centerPatchDTO.getStatus() != null) {
                center.setStatus(centerPatchDTO.getStatus());
            }
            if (centerPatchDTO.getCurrentLoad() != null) {
                center.setCurrentLoad(centerPatchDTO.getCurrentLoad());
            }
            if (centerPatchDTO.getMaxCapacity() != null) {
                center.setMaxCapacity(centerPatchDTO.getMaxCapacity());
            }
            if (centerPatchDTO.getCoordinates() != null) {
                Coordinates coordinates = new Coordinates();
                coordinates.setLatitude(centerPatchDTO.getCoordinates().getLatitude());
                coordinates.setLongitude(centerPatchDTO.getCoordinates().getLongitude());
                center.setCoordinates(coordinates);
            }
            centerRepository.save(center);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Transactional
    public void deleteCenter(Long id) throws Exception {
        try {
            Center center = centerRepository.findById(id).orElseThrow(() -> new Exception("Center not found"));
            centerRepository.delete(center);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
