package hr.tvz.ceperic.airqualityapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;

public interface Sgp40Repository extends JpaRepository<Sgp40Reading, Long> {

    Optional<Sgp40Reading> findTopByOrderByTimestampDesc();

    List<Sgp40Reading> findByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value="""
            SELECT
                ROUND(AVG(VOC_INDEX), 3) as VOC_INDEX,
                'ok' as status,
                DATE_TRUNC('HOUR', timestamp) as hour_timestamp
            FROM sgp40_reading
            WHERE timestamp BETWEEN :start AND :end 
            AND status='ok'
            GROUP BY DATE_TRUNC('HOUR', timestamp)
            ORDER BY hour_timestamp    
            """, nativeQuery = true)
    List<Object[]> getHourlyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
                                    
    @Query(value="""
            SELECT
                ROUND(AVG(VOC_INDEX), 3) as VOC_INDEX,
                status,
                DATE(timestamp) as timestamp
            FROM sgp40_reading
            WHERE timestamp BETWEEN :start AND :end
            AND status='ok'
            GROUP BY DATE(timestamp)
            ORDER BY timestamp    
            """, nativeQuery = true)
    List<Object[]> getDailyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
}
