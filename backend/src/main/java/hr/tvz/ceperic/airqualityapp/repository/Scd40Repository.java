package hr.tvz.ceperic.airqualityapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;

public interface Scd40Repository extends JpaRepository<Scd40Reading, Long> {

    Optional<Scd40Reading> findTopByOrderByTimestampDesc();
    
    List<Scd40Reading> findByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value="""
            SELECT
                ROUND(AVG(co2), 3) as co2,
                ROUND(AVG(temperature), 3) as temperature,
                ROUND(AVG(humidity), 3) as humidity,
                'ok' as status,
                DATE_TRUNC('HOUR', timestamp) as hour_timestamp
            FROM scd40_reading
            WHERE timestamp BETWEEN :start AND :end 
            AND status='ok'
            GROUP BY DATE_TRUNC('HOUR', timestamp)
            ORDER BY hour_timestamp    
            """, nativeQuery = true)
    List<Object[]> getHourlyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
                                    
    @Query(value="""
            SELECT
                ROUND(AVG(co2), 3) as co2,
                ROUND(AVG(temperature), 3) as temperature,
                ROUND(AVG(humidity), 3) as humidity,
                status,
                DATE(timestamp) as timestamp
            FROM scd40_reading
            WHERE timestamp BETWEEN :start AND :end
            AND status='ok'
            GROUP BY DATE(timestamp)
            ORDER BY timestamp    
            """, nativeQuery = true)
    List<Object[]> getDailyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
}
