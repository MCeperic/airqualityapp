package hr.tvz.ceperic.airqualityapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;

public interface Pms5003Repository extends JpaRepository<Pms5003Reading, Long>{

    @Query(value = "SELECT * FROM pms5003_reading ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    Optional<Pms5003Reading> fetchLatestReading();

    Optional<Pms5003Reading> findTopByOrderByTimestampDesc();
}
