package com.example.demo.dto;

import java.time.LocalDateTime;

public class SensorReadingDTO {

    private String sensorType;
    private Double value;
    private LocalDateTime timestamp;

    public SensorReadingDTO(String sensorType, Double value, LocalDateTime timestamp) {
        this.sensorType = sensorType;
        this.value = value;
        this.timestamp = timestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
