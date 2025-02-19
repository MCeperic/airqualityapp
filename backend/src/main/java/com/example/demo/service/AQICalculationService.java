package com.example.demo.service;

import com.example.demo.model.SensorReading;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AQICalculationService {



    public Integer calculateAQI(List<SensorReading> latestReadings){
        Integer highestAQI = 0;

        for (SensorReading readings : latestReadings){
            Integer aqi = calculateIndividualAQI(readings);
            highestAQI = Math.max(highestAQI, aqi);
        }

        return highestAQI;
    }

    private Integer calculateIndividualAQI(SensorReading reading){
        Double value = reading.getSensorValue();
        String type = reading.getSensorType();

        switch (type) {
            case "PM2.5":
                return (value <= 10.0) ? 1
                        : (value <= 20.0) ? 2
                        : (value <= 25.0) ? 3
                        : (value <= 50.0) ? 4
                        : (value <= 75.0) ? 5
                        : 6;
            case "PM10":
                return (value <= 20.0) ? 1
                        : (value <= 40.0) ? 2
                        : (value <= 50.0) ? 3
                        : (value <= 100.0) ? 4
                        : (value <= 150.0) ? 5
                        : 6;
            case "NO2":
                return (value <= 40.0) ? 1
                        : (value <= 90.0) ? 2
                        : (value <= 120.0) ? 3
                        : (value <= 230.0) ? 4
                        : (value <= 340.0) ? 5
                        : 6;
            default:
                return 0;
        }
    }
}
