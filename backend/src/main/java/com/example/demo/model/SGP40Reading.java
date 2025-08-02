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
@Table(name = "SGP40_reading")
public class SGP40Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voc_index")
    private Integer vocIndex;

    private String status;

    private LocalDateTime timestamp;
}
