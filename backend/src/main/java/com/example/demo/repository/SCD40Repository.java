package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.SCD40Reading;

public interface SCD40Repository extends JpaRepository<SCD40Reading, Long> {
    
}
