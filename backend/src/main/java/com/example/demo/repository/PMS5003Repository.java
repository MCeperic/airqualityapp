package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.PMS5003Reading;

public interface PMS5003Repository extends JpaRepository<PMS5003Reading, Long>{
    
}
