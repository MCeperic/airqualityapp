package com.example.demo.config;

import com.example.demo.model.SensorReading;
import com.example.demo.repository.SensorReadingRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
public class SensorDataSeeder implements CommandLineRunner {

    private final SensorReadingRepository repository;
    private final Random random = new Random();

    private Double co2 = 400.0;
    private Double pm2point5 = 15.0;
    private Double pm10 = 25.0;

    public SensorDataSeeder(SensorReadingRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        LocalDateTime timestamp = LocalDateTime.now().minusDays(10);
        Integer entries = 1440;
        

        for (int i = 0; i < entries; i++) {
            timestamp = timestamp.plusMinutes(10);

            co2 = adjustValue(co2, 10.0);
            pm2point5 = adjustValue(pm2point5, 2.0);
            pm10 = adjustValue(pm10, 3.0);

            List<SensorReading> readings = List.of(
                    new SensorReading("CO2", co2, timestamp),
                    new SensorReading("PM2.5", pm2point5, timestamp),
                    new SensorReading("PM10", pm10, timestamp)
            );

            repository.saveAll(readings);
        }
    }

    private Double adjustValue(Double value, Double fluctuation){
        Double change = (random.nextDouble() * 2.0 - 1.0) * fluctuation;
        Double newValue = value + change;
        return Math.max(0, Math.round(newValue * 1000.0) / 1000.0);
    }
}
