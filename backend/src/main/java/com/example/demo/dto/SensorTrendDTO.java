package com.example.demo.dto;

import java.time.LocalDateTime;

public class SensorTrendDTO {

    private String sensorType;
    private Double latestValue;
    private Double previousValue;
    private String trend;
    private LocalDateTime currentTimestamp;

    public SensorTrendDTO(String sensorType, Double latestValue, Double previousValue, LocalDateTime currentTimestamp) {
        this.sensorType = sensorType;
        this.latestValue = latestValue;
        this.previousValue = previousValue;
        this.trend = (latestValue > previousValue) ? "up" : (latestValue < previousValue) ? "down" : "stable";
        this.currentTimestamp = currentTimestamp;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public Double getLatestValue() {
        return latestValue;
    }

    public void setLatestValue(Double latestValue) {
        this.latestValue = latestValue;
    }

    public Double getPreviousValue() {
        return previousValue;
    }

    public void setPreviousValue(Double previousValue) {
        this.previousValue = previousValue;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public LocalDateTime getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(LocalDateTime currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }
}
