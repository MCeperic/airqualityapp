package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SGP40Reading;

public interface SGP40Repository extends JpaRepository<SGP40Reading, Long> {
    
}
