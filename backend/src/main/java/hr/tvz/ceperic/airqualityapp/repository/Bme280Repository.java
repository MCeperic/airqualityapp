package hr.tvz.ceperic.airqualityapp.repository;

import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Bme280Repository extends JpaRepository<Bme280Reading, Long> {

    Optional<Bme280Reading> findTopByOrderByTimestampDesc();

    List<Bme280Reading> findByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value="""
            SELECT
                ROUND(AVG(temperature), 3) as temperature,
                ROUND(AVG(humidity), 3) as humidity,
                ROUND(AVG(pressure), 3) as pressure,
                'ok' as status,
                DATE_TRUNC('HOUR', timestamp) as hour_timestamp
            FROM bme280_reading
            WHERE timestamp BETWEEN :start AND :end
            AND status='ok'
            GROUP BY DATE_TRUNC('HOUR', timestamp)
            ORDER BY hour_timestamp    
            """, nativeQuery = true)
    List<Object[]> getHourlyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
                                    
    @Query(value="""
            SELECT
                ROUND(AVG(temperature), 3) as temperature,
                ROUND(AVG(humidity), 3) as humidity,
                ROUND(AVG(pressure), 3) as pressure,
                status,
                DATE(timestamp) as timestamp
            FROM bme280_reading
            WHERE timestamp BETWEEN :start AND :end
            AND status='ok'
            GROUP BY DATE(timestamp)
            ORDER BY timestamp    
            """, nativeQuery = true)
    List<Object[]> getDailyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);

}
