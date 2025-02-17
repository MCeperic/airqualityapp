package com.example.demo.service;

import com.example.demo.dto.SensorReadingDTO;
import com.example.demo.mapper.SensorReadingMapper;
import com.example.demo.model.SensorReading;
import com.example.demo.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorReadingService {

    private final SensorReadingRepository repository;
    private final SensorReadingMapper mapper;

    public SensorReadingService(SensorReadingRepository repository, SensorReadingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SensorReadingDTO> getAllReadings(){
        return mapper.toDTOList(repository.findAll());
    }

    public List<SensorReadingDTO> getReadingsByType(String type){
        return mapper.toDTOList(repository.findBySensorType(type));
    }

    public SensorReadingDTO saveReading(SensorReadingDTO reading){
        return mapper.toDTO(repository.save(mapper.toEntity(reading)));
    }


}
