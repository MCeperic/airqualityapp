package com.example.demo.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@Table(name = "BME280_reading")
public class BME280Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double temperature;

    private Double humidity;

    private Double pressure;

    private String status;
    
    private LocalDateTime timestamp;
}