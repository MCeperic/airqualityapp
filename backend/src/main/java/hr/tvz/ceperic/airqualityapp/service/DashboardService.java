package hr.tvz.ceperic.airqualityapp.service;

import org.springframework.stereotype.Service;

import hr.tvz.ceperic.airqualityapp.dto.SensorReadingsDto;
import hr.tvz.ceperic.airqualityapp.mapper.SensorReadingMapper;
import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;
import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;
import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;
import hr.tvz.ceperic.airqualityapp.repository.Bme280Repository;
import hr.tvz.ceperic.airqualityapp.repository.Pms5003Repository;
import hr.tvz.ceperic.airqualityapp.repository.Scd40Repository;
import hr.tvz.ceperic.airqualityapp.repository.Sgp40Repository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class DashboardService {
    
    private final Bme280Repository bme280Repository;
    private final Pms5003Repository pms5003Repository;
    private final Scd40Repository scd40Repository;
    private final Sgp40Repository sgp40Repository;

    public SensorReadingsDto getLatestReadings() {
        log.debug("Fetching latest sensor readings");
/* 
        Bme280Reading bme280Reading = bme280Repository.findTopByOrderByTimestampDesc().orElseThrow(() -> new RuntimeException("No BME280 data found"));
        Pms5003Reading pms5003Reading = pms5003Repository.findTopByOrderByTimestampDesc().orElseThrow(() -> new RuntimeException("No PMS5003 data found"));
        Scd40Reading scd40Reading = scd40Repository.findTopByOrderByTimestampDesc().orElseThrow(() -> new RuntimeException("No SCD40 data found"));
        Sgp40Reading sgp40Reading = sgp40Repository.findTopByOrderByTimestampDesc().orElseThrow(() -> new RuntimeException("No SGP40 data found"));
 */
        Bme280Reading bme280Reading = bme280Repository.findById(12501L).orElseThrow(() -> new RuntimeException("No BME280 data found"));
        Pms5003Reading pms5003Reading = pms5003Repository.findById(12454L).orElseThrow(() -> new RuntimeException("No PMS5003 data found"));
        Scd40Reading scd40Reading = scd40Repository.findById(12501L).orElseThrow(() -> new RuntimeException("No SCD40 data found"));
        Sgp40Reading sgp40Reading = sgp40Repository.findById(12501L).orElseThrow(() -> new RuntimeException("No SGP40 data found"));

        
        return SensorReadingsDto.builder()
        .bme280Dto(SensorReadingMapper.toBme280Dto(bme280Reading))
        .pms5003Dto(SensorReadingMapper.toPms5003Dto(pms5003Reading))
        .scd40Dto(SensorReadingMapper.toScd40Dto(scd40Reading))
        .sgp40Dto(SensorReadingMapper.toSgp40Dto(sgp40Reading))
        .build();
    }
}
