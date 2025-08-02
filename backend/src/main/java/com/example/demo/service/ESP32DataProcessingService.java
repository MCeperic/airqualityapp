package com.example.demo.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.example.demo.model.BME280Reading;
import com.example.demo.model.PMS5003Reading;
import com.example.demo.model.SCD40Reading;
import com.example.demo.model.SGP40Reading;
import com.example.demo.repository.BME280Repository;
import com.example.demo.repository.PMS5003Repository;
import com.example.demo.repository.SCD40Repository;
import com.example.demo.repository.SGP40Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
@Slf4j
public class ESP32DataProcessingService {

    private final BME280Repository bme280Repository;
    private final SGP40Repository sgp40Repository;
    private final SCD40Repository scd40Repository;
    private final PMS5003Repository pms5003Repository;

    public void recieveAndProcessData(String jsonData){
        ObjectMapper objectMapper = new ObjectMapper();
        LocalDateTime timestamp = LocalDateTime.now();
        
        try {
            JsonNode mainNode = objectMapper.readTree(jsonData);
            log.info("Processing ESP32 data recevied at: {}", timestamp);
            
            processBME280Data(mainNode, timestamp);
            processSCD40Data(mainNode, timestamp);
            processSGP40Data(mainNode, timestamp);
            processPMS5003Data(mainNode, timestamp);
            
        } catch (JsonProcessingException e) {
            log.error("Failed to parse JSON: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error parsing ESP32 data: {}", e.getMessage());
        }
    }
    
    private void processBME280Data(JsonNode mainNode, LocalDateTime timestamp){
        if (!mainNode.has("bme280")){
            log.error("JSON is missing BME280 node!");
            return;
        }
        JsonNode bme280Node = mainNode.get("bme280");
        String status = bme280Node.get("status").asText();

        if (!"ok".equals(status)){
            log.error("BME280 status is {}", status);
            return;
        }
        try{
            BME280Reading bme280Reading = BME280Reading.builder()
                                        .temperature(bme280Node.get("temperature").doubleValue())
                                        .humidity(bme280Node.get("humidity").doubleValue())
                                        .pressure(bme280Node.get("pressure").doubleValue())
                                        .status(status)
                                        .timestamp(timestamp)
                                        .build();
            bme280Repository.save(bme280Reading);
        } catch (Exception e){
            log.error("Error processing BME280 data: {}", e.getMessage());
        }
    }

    private void processSCD40Data(JsonNode mainNode, LocalDateTime timestamp){
        if (!mainNode.has("scd40")){
            log.error("JSON is missing SCD40 node!");
            return;
        }
        JsonNode scd40Node = mainNode.get("scd40");
        String status = scd40Node.get("status").asText();

        if (!"ok".equals(status)){
            log.error("SCD40 status is {}", status);
            return;
        }
        try{
            SCD40Reading scd40Reading = SCD40Reading.builder()
                                        .co2(scd40Node.get("co2").intValue())
                                        .temperature(scd40Node.get("temp").doubleValue())
                                        .humidity(scd40Node.get("humidity").doubleValue())
                                        .status(status)
                                        .timestamp(timestamp)
                                        .build();

            scd40Repository.save(scd40Reading);
        } catch (Exception e){
            log.error("Error processing SCD40 data: {}", e.getMessage());
        }
    }

    private void processSGP40Data(JsonNode mainNode, LocalDateTime timestamp){
        if (!mainNode.has("sgp40")){
            log.error("JSON is missing SGP40 node!");
            return;
        }
        JsonNode sgp40Node = mainNode.get("sgp40");
        String status = sgp40Node.get("status").asText();

        if (!"ok".equals(status)){
            log.error("SGP40 status is {}", status);
            return;
        }
        try{
            SGP40Reading sgp40Reading = SGP40Reading.builder()
                                        .vocIndex(sgp40Node.get("voc_index").intValue())
                                        .status(status)
                                        .timestamp(timestamp)
                                        .build();

            sgp40Repository.save(sgp40Reading);                        
        } catch (Exception e){
            log.error("Error processing SGP40 data: {}", e.getMessage());
        }
    }

    private void processPMS5003Data(JsonNode mainNode, LocalDateTime timestamp){
        if (!mainNode.has("pms5003")){
            log.error("JSON is missing PMS5003 node!");
            return;
        }
        JsonNode pms5003Node = mainNode.get("pms5003");
        String status = pms5003Node.get("status").asText();

        if (!"ok".equals(status)){
            log.error("PMS5003 status is {}", status);
            return;
        }
        try{
            PMS5003Reading pms5003Reading = PMS5003Reading.builder()
                                            .pm1(pms5003Node.get("pm1_0").intValue())
                                            .pm25(pms5003Node.get("pm2_5").intValue())
                                            .pm10(pms5003Node.get("pm10").intValue())
                                            .status(status)
                                            .timestamp(timestamp)
                                            .build();

            pms5003Repository.save(pms5003Reading);                               
        } catch (Exception e){
            log.error("Error processing PMS5003 data: {}", e.getMessage());
        }
    }
}
