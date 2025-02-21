package com.example.demo.repository;

import com.example.demo.model.SensorReading;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Long> {

    String GET_HOURLY_READINGS_QUERY = """
            SELECT sensor_type,
                AVG(sensor_value) AS avg_value,
                DATE_TRUNC('HOUR', timestamp) AS rounded_timestamp
            FROM sensor_reading
            WHERE sensor_type = :sensorType AND timestamp >= :timeSelection
            GROUP BY sensor_type, rounded_timestamp
            ORDER BY rounded_timestamp
            """;
    String GET_DAILY_READINGS_QUERY = """
            SELECT sensor_type,
                AVG(sensor_value) AS avg_value,
                DATE_TRUNC('DAY', timestamp) AS rounded_timestamp
            FROM sensor_reading
            WHERE sensor_type = :sensorType AND timestamp >= :timeSelection
            GROUP BY sensor_type, rounded_timestamp
            ORDER BY rounded_timestamp
            """;


    List<SensorReading> findBySensorType(String sensorType);

    @Query("SELECT s FROM SensorReading s ORDER BY s.timestamp DESC")
    List<SensorReading> findLatestReadings(Pageable pageable);

    @Query(value = GET_HOURLY_READINGS_QUERY, nativeQuery = true)
    List<Object[]> getHourlyReadings(@Param("sensorType") String sensorType, @Param("timeSelection")LocalDateTime timeSelection);

    @Query(value = GET_DAILY_READINGS_QUERY, nativeQuery = true)
    List<Object[]> getDailyReadings(@Param("sensorType") String sensorType, @Param("timeSelection")LocalDateTime timeSelection);
}
