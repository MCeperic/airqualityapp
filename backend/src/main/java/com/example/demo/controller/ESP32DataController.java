package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ESP32DataProcessingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class ESP32DataController {
    
    private final ESP32DataProcessingService esp32DataProcessingService;

    @PostMapping("/sensor-data")
    public ResponseEntity<String> recieveSensorData(@RequestBody String jsonData){
        esp32DataProcessingService.recieveAndProcessData(jsonData);
        return ResponseEntity.ok("Data recieved!");
    }
}
