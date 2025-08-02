package hr.tvz.ceperic.airqualityapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;

public interface Pms5003Repository extends JpaRepository<Pms5003Reading, Long>{
    
}
