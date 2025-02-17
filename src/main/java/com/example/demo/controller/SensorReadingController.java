package com.example.demo.controller;

import com.example.demo.dto.SensorReadingDTO;
import com.example.demo.service.SensorReadingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/aqa")
public class SensorReadingController {

    private final SensorReadingService service;

    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }

    @GetMapping("/readings")
    public ResponseEntity<List<SensorReadingDTO>> getAllReadings(){
        List<SensorReadingDTO> readings = service.getAllReadings();
        if (readings.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(readings);
    }
    @GetMapping("/readings/{type}")
    public ResponseEntity<List<SensorReadingDTO>> getAllReadingsByType(@PathVariable String type){
        List<SensorReadingDTO> readings = service.getReadingsByType(type);
        if (readings.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(readings);
    }

    @PostMapping("/readings")
    public ResponseEntity<SensorReadingDTO> saveReading (@RequestBody SensorReadingDTO sensorReadingDTO){
        service.saveReading(sensorReadingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sensorReadingDTO);
    }
}
