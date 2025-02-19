package com.example.demo.repository;

import com.example.demo.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    List<SensorReading> findBySensorType(String sensorType);

    @Query("SELECT s FROM SensorReading s WHERE s.timestamp = (SELECT MAX(s2.timestamp) FROM SensorReading s2)")
    List<SensorReading> findLatestReadings();
}
