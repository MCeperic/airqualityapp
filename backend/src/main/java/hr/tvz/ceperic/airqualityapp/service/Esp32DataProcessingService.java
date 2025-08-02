package hr.tvz.ceperic.airqualityapp.service;

import java.time.LocalDateTime;

import hr.tvz.ceperic.airqualityapp.repository.Pms5003Repository;
import hr.tvz.ceperic.airqualityapp.repository.Scd40Repository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;
import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;
import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;
import hr.tvz.ceperic.airqualityapp.repository.Bme280Repository;
import hr.tvz.ceperic.airqualityapp.repository.Sgp40Repository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
@Slf4j
public class Esp32DataProcessingService {

    private final Bme280Repository bme280Repository;
    private final Sgp40Repository sgp40Repository;
    private final Scd40Repository scd40Repository;
    private final Pms5003Repository pms5003Repository;

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
            Bme280Reading bme280Reading = Bme280Reading.builder()
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
            Scd40Reading scd40Reading = Scd40Reading.builder()
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
            Sgp40Reading sgp40Reading = Sgp40Reading.builder()
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
            Pms5003Reading pms5003Reading = Pms5003Reading.builder()
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
