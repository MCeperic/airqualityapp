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
@Table(name = "SCD40_reading")
public class SCD40Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer co2;

    private Double temperature;

    private Double humidity;

    private String status;

    private LocalDateTime timestamp;
}
