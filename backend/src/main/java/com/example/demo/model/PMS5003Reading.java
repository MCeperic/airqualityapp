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
@Table(name = "PMS5003_reading")
public class PMS5003Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pm1_0")
    private Integer pm1;
    
    @Column(name = "pm2_5")
    private Integer pm25;

    private Integer pm10;
    
    private String status;

    private LocalDateTime timestamp;
}

