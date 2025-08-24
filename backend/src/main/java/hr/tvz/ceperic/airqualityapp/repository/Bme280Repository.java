package hr.tvz.ceperic.airqualityapp.repository;

import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Bme280Repository extends JpaRepository<Bme280Reading, Long> {

    Optional<Bme280Reading> findTopByOrderByTimestampDesc();

}
