package com.example.demo.controller;

import com.example.demo.dto.SensorReadingDTO;
import com.example.demo.service.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aqa/history")
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping
    public ResponseEntity<List<SensorReadingDTO>> getHistoricalData(
            @RequestParam String sensorType,
            @RequestParam String range) {

        List<SensorReadingDTO> data = historyService.getHistoricalData(sensorType, range);
        return ResponseEntity.ok(data);
    }
}
