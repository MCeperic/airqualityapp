package hr.tvz.ceperic.airqualityapp.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;

public interface Pms5003Repository extends JpaRepository<Pms5003Reading, Long>{

    Optional<Pms5003Reading> findTopByOrderByTimestampDesc();

    List<Pms5003Reading> findByTimestampBetweenOrderByTimestamp(LocalDateTime start, LocalDateTime end);

    @Query(value="""
            SELECT
                ROUND(AVG(pm1_0), 3) as pm1_0,
                ROUND(AVG(pm2_5), 3) as pm2_5,
                ROUND(AVG(pm10), 3) as pm10,
                'ok' as status,
                DATE_TRUNC('HOUR', timestamp) as hour_timestamp
            FROM pms5003_reading
            WHERE timestamp BETWEEN :start AND :end 
            AND status='ok'
            GROUP BY DATE_TRUNC('HOUR', timestamp)
            ORDER BY hour_timestamp    
            """, nativeQuery = true)
    List<Object[]> getHourlyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
                                    
    @Query(value="""
            SELECT
                ROUND(AVG(pm1_0), 3) as pm1_0,
                ROUND(AVG(pm2_5), 3) as pm2_5,
                ROUND(AVG(pm10), 3) as pm10,
                status,
                DATE(timestamp) as timestamp
            FROM pms5003_reading
            WHERE timestamp BETWEEN :start AND :end
            AND status='ok'
            GROUP BY DATE(timestamp)
            ORDER BY timestamp    
            """, nativeQuery = true)
    List<Object[]> getDailyAverages(@Param("start") LocalDateTime start, 
                                    @Param("end") LocalDateTime end);
}
