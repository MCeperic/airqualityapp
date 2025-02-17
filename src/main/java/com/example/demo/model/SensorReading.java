package com.example.demo.model;

import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
public class SensorReading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sensorType;

    @Column(name = "sensor_value")
    private Double sensorValue;
    private LocalDateTime timestamp;

    public SensorReading() {
    }

    public SensorReading(String sensorType, Double sensorValue, LocalDateTime timestamp) {
        this.sensorType = sensorType;
        this.sensorValue = sensorValue;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSensorValue() {
        return sensorValue;
    }

    public void setSensorValue(Double sensorValue) {
        this.sensorValue = sensorValue;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
