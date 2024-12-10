package com.hackathon.inditex.Controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.inditex.Services.CenterService;
import com.hackathon.inditex.models.center.CenterDTO;
import com.hackathon.inditex.models.center.CenterPatchDTO;

@RestController
@RequestMapping("/api")
public class CenterController {
    @Autowired
    private CenterService centerService;

    @PostMapping("/centers")
    public ResponseEntity<Object> createCenter(@RequestBody CenterDTO centerDTO) {
        try {
            centerService.createCenter(centerDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logistics center created successfully.");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/centers")
    public ResponseEntity<Object> getCenters() {
        try {
            return ResponseEntity.ok().body(centerService.getCenters());
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PatchMapping("/centers/{id}")
    public ResponseEntity<Object> updateCenter(@PathVariable Long id, @RequestBody CenterPatchDTO centerDTO) {
        try {
            centerService.updateCenter(id, centerDTO);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logistics center updated successfully.");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/centers/{id}")
    public ResponseEntity<Object> deleteCenter(@PathVariable Long id) {
        try {
            centerService.deleteCenter(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Logistics center deleted successfully.");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
