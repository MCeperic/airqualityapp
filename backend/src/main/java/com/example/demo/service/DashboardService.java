package com.example.demo.service;

import com.example.demo.dto.SensorTrendDTO;
import com.example.demo.model.SensorReading;
import com.example.demo.repository.SensorReadingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final SensorReadingRepository repository;
    private final AQICalculationService aqiCalculationService;

    public DashboardService(SensorReadingRepository repository, AQICalculationService aqiCalculationService) {
        this.repository = repository;
        this.aqiCalculationService = aqiCalculationService;
    }

    public Integer getLatestAQI() {
        List<SensorReading> latestReadings = repository.findLatestReadings(PageRequest.of(0, 3));
        if (latestReadings.isEmpty()){
            throw new RuntimeException("No data avaliable!");
        }

        return aqiCalculationService.calculateAQI(latestReadings);
    }

    public List<SensorTrendDTO> getLatestReadingsWithTrends() {
        List<SensorReading> readings = repository.findLatestReadings(PageRequest.of(0, 6));

        if (readings.size() < 6){
            return Collections.emptyList();
        }

        List<SensorReading> currentBatch = readings.subList(0, 3);
        List<SensorReading> previousBatch = readings.subList(3, 6);

        return currentBatch.stream()
                .map(current -> {
                    SensorReading previous = previousBatch.stream()
                            .filter(prev -> prev.getSensorType().equals(current.getSensorType()))
                            .findFirst()
                            .orElse(null);

                    Double previousValue = (previous != null) ? previous.getSensorValue() : 0.0;

                    return new SensorTrendDTO(
                            current.getSensorType(),
                            current.getSensorValue(),
                            previousValue,
                            current.getTimestamp());
                })
                .toList();
    }
}
