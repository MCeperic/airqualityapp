package hr.tvz.ceperic.airqualityapp.mapper;

import org.springframework.stereotype.Component;

import hr.tvz.ceperic.airqualityapp.dto.Bme280Dto;
import hr.tvz.ceperic.airqualityapp.dto.Pms5003Dto;
import hr.tvz.ceperic.airqualityapp.dto.Scd40Dto;
import hr.tvz.ceperic.airqualityapp.dto.Sgp40Dto;
import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;
import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;
import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;

@Component
public class SensorReadingMapper {

    public static Bme280Dto toBme280Dto(Bme280Reading bme280Reading){
        return Bme280Dto.builder()
        .temperature(bme280Reading.getTemperature())
        .humidity(bme280Reading.getHumidity())
        .pressure(bme280Reading.getPressure())
        .status(bme280Reading.getStatus())
        .timestamp(bme280Reading.getTimestamp())
        .build();
    }

    public static Pms5003Dto toPms5003Dto(Pms5003Reading pms5003Reading){
        return Pms5003Dto.builder()
        .pm1(pms5003Reading.getPm1())
        .pm2_5(pms5003Reading.getPm2_5())
        .pm10(pms5003Reading.getPm10())
        .status(pms5003Reading.getStatus())
        .timestamp(pms5003Reading.getTimestamp())
        .build();
    }

    public static Scd40Dto toScd40Dto(Scd40Reading scd40Reading){
        return Scd40Dto.builder()
        .co2(scd40Reading.getCo2())
        .temperature(scd40Reading.getTemperature())
        .humidity(scd40Reading.getHumidity())
        .status(scd40Reading.getStatus())
        .timestamp(scd40Reading.getTimestamp())
        .build();
    }

    public static Sgp40Dto toSgp40Dto(Sgp40Reading sgp40Reading){
        return Sgp40Dto.builder()
        .vocIndex(sgp40Reading.getVocIndex())
        .status(sgp40Reading.getStatus())
        .timestamp(sgp40Reading.getTimestamp())
        .build();
    }
}
