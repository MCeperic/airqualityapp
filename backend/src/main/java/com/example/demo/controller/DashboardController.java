package com.example.demo.controller;

import com.example.demo.repository.SensorReadingRepository;
import com.example.demo.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aqa/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/aqi")
    public ResponseEntity<Integer> getAQI() {
        Integer aqi = service.getLatestAQI();
        return ResponseEntity.ok(aqi);
    }
}
