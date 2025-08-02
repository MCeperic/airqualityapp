package com.example.demo.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.model.BME280Reading;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface BME280Repository extends JpaRepository<BME280Reading, Long> {

}
