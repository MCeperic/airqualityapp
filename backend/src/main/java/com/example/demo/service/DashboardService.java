package com.example.demo.service;

import com.example.demo.model.SensorReading;
import com.example.demo.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DashboardService {

    private final SensorReadingRepository repository;
    private final AQICalculationService aqiCalculationService;

    public DashboardService(SensorReadingRepository repository, AQICalculationService aqiCalculationService) {
        this.repository = repository;
        this.aqiCalculationService = aqiCalculationService;
    }

    public Integer getLatestAQI() {
        List<SensorReading> latestReadings = repository.findLatestReadings();
        if (latestReadings.isEmpty()){
            throw new RuntimeException("No data avaliable!");
        }

        return aqiCalculationService.calculateAQI(latestReadings);
    }


}
