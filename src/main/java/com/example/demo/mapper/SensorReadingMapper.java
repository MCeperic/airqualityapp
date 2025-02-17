package com.example.demo.mapper;

import com.example.demo.dto.SensorReadingDTO;
import com.example.demo.model.SensorReading;

import java.util.List;

public class SensorReadingMapper {

    public SensorReadingDTO toDTO(SensorReading sensorReading){
        return new SensorReadingDTO(sensorReading.getSensorType(),
                sensorReading.getValue(), sensorReading.getTimestamp());
    }
    public List<SensorReadingDTO> toDTOList (List<SensorReading> sensorReadingList){
        return sensorReadingList.stream().map(this::toDTO).toList();
    }
    public SensorReading toEntity(SensorReadingDTO sensorReadingDTO){
        return new SensorReading(sensorReadingDTO.getSensorType(),
                sensorReadingDTO.getValue(), sensorReadingDTO.getTimestamp());
    }
}
