package hr.tvz.ceperic.airqualityapp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;

public interface Scd40Repository extends JpaRepository<Scd40Reading, Long> {
    Optional<Scd40Reading> findTopByOrderByTimestampDesc();
}
