package com.example.demo.service;

import com.example.demo.dto.SensorReadingDTO;
import com.example.demo.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistoryService {
    private SensorReadingRepository repository;

    public HistoryService(SensorReadingRepository repository) {
        this.repository = repository;
    }

    public List<SensorReadingDTO> getHistoricalData(String sensorType, String range){
        System.out.println("Fetching data for " + sensorType + " with range " + range);

        LocalDateTime time = calucalateTime(range);

        List<Object[]> data;

        if(range.equals("24h")){
            data = repository.getHourlyReadings(sensorType, time);
        } else {
            data = repository.getDailyReadings(sensorType, time);
        }
        System.out.println("Managed to fetch data for " + sensorType);

        return data.stream().map(row -> new SensorReadingDTO(
                (String) row[0],
                ((BigDecimal) row[1]).doubleValue(),
                ((Timestamp) row[2]).toLocalDateTime()
        )).toList();

    }

    private LocalDateTime calucalateTime(String range){
        return range.equals("24h") ? LocalDateTime.now().minusHours(24) : LocalDateTime.now().minusDays(7);
    }
}
