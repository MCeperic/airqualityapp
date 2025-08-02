package hr.tvz.ceperic.airqualityapp.repository;

import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface Bme280Repository extends JpaRepository<Bme280Reading, Long> {

}
