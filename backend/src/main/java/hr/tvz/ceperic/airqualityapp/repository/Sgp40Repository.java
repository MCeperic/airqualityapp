package hr.tvz.ceperic.airqualityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;

public interface Sgp40Repository extends JpaRepository<Sgp40Reading, Long> {
    
}
