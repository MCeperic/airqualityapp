package hr.tvz.ceperic.airqualityapp.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import hr.tvz.ceperic.airqualityapp.model.Bme280Reading;
import hr.tvz.ceperic.airqualityapp.model.Pms5003Reading;
import hr.tvz.ceperic.airqualityapp.model.Scd40Reading;
import hr.tvz.ceperic.airqualityapp.model.Sgp40Reading;
import hr.tvz.ceperic.airqualityapp.repository.Bme280Repository;
import hr.tvz.ceperic.airqualityapp.repository.Pms5003Repository;
import hr.tvz.ceperic.airqualityapp.repository.Scd40Repository;
import hr.tvz.ceperic.airqualityapp.repository.Sgp40Repository;
import hr.tvz.ceperic.airqualityapp.dto.*;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class HistoryService {

    private final Bme280Repository bme280Repository;
    private final Pms5003Repository pms5003Repository;
    private final Scd40Repository scd40Repository;
    private final Sgp40Repository sgp40Repository;

    public SensorReadingsTimeSeriesDto getEveryMinute(LocalDate date, Integer hour){
        LocalDateTime start = date.atTime(hour, 0);
        LocalDateTime end = start.plusHours(1);
        
        List<Bme280Reading> bme280List = bme280Repository.findByTimestampBetweenOrderByTimestamp(start, end);
        List<Pms5003Reading> pms5003List = pms5003Repository.findByTimestampBetweenOrderByTimestamp(start, end);
        List<Scd40Reading> scd40List = scd40Repository.findByTimestampBetweenOrderByTimestamp(start, end);
        List<Sgp40Reading> sgp40List = sgp40Repository.findByTimestampBetweenOrderByTimestamp(start, end);

        Map<LocalDateTime, Bme280Dto> bme280Map = bme280List.stream()
                                                    .collect(Collectors.toMap(
                                                        reading -> reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES),
                                                        reading -> {
                                                            LocalDateTime truncatedTimestamp = reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES);
                                                            return Bme280Dto.builder()
                                                            .temperature(reading.getTemperature())
                                                            .humidity(reading.getHumidity())
                                                            .pressure(reading.getPressure())
                                                            .status(reading.getStatus())
                                                            .timestamp(truncatedTimestamp)
                                                            .build();
                                                        },
                                                        (existing, replacement) -> existing
                                                        ));

        Map<LocalDateTime, Pms5003Dto> pms5003Map = pms5003List.stream()
                                                    .collect(Collectors.toMap(
                                                        reading -> reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES),
                                                        reading -> {
                                                            LocalDateTime truncatedTimestamp = reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES);
                                                            return Pms5003Dto.builder()
                                                            .pm1(reading.getPm1())
                                                            .pm2_5(reading.getPm2_5())
                                                            .pm10(reading.getPm10())
                                                            .status(reading.getStatus())
                                                            .timestamp(truncatedTimestamp)
                                                            .build();
                                                        },
                                                        (existing, replacement) -> existing
                                                        ));
                                                        
        Map<LocalDateTime, Scd40Dto> scd40Map = scd40List.stream()
                                                    .collect(Collectors.toMap(
                                                        reading -> reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES),
                                                        reading -> {
                                                            LocalDateTime truncatedTimestamp = reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES);
                                                            return Scd40Dto.builder()
                                                            .co2(reading.getCo2())
                                                            .temperature(reading.getTemperature())
                                                            .humidity(reading.getHumidity())
                                                            .status(reading.getStatus())
                                                            .timestamp(truncatedTimestamp)
                                                            .build();
                                                        },
                                                        (existing, replacement) -> existing
                                                        ));
                                                        
        Map<LocalDateTime, Sgp40Dto> sgp40Map = sgp40List.stream()
                                                    .collect(Collectors.toMap(
                                                        reading -> reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES),
                                                        reading -> {
                                                            LocalDateTime truncatedTimestamp = reading.getTimestamp().truncatedTo(ChronoUnit.MINUTES);
                                                            return Sgp40Dto.builder()
                                                            .vocIndex(reading.getVocIndex())
                                                            .status(reading.getStatus())
                                                            .timestamp(truncatedTimestamp)
                                                            .build();
                                                        },
                                                        (existing, replacement) -> existing
                                                        ));
                                                        
        List<SensorReadingsDto> data = IntStream.range(0, 60)
                                        .mapToObj(i -> {
                                            LocalDateTime minute = start.plusMinutes(i);

                                            return SensorReadingsDto.builder()
                                            .bme280Dto(bme280Map.get(minute))
                                            .pms5003Dto(pms5003Map.get(minute))
                                            .scd40Dto(scd40Map.get(minute))
                                            .sgp40Dto(sgp40Map.get(minute))
                                            .build();
                                        })
                                        .toList();

        return SensorReadingsTimeSeriesDto.builder()
        .data(data)
        .timeRange("minute")
        .startTime(start)
        .endTime(end)
        .build();
    }

    public SensorReadingsTimeSeriesDto getHourly(LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        List<Object[]> bme280List = bme280Repository.getHourlyAverages(start, end);
        List<Object[]> pms5003List = pms5003Repository.getHourlyAverages(start, end);
        List<Object[]> scd40List = scd40Repository.getHourlyAverages(start, end);
        List<Object[]> sgp40List = sgp40Repository.getHourlyAverages(start, end);

        Map<LocalDateTime, Bme280Dto> bme280Map = bme280List.stream()
        .collect(Collectors.toMap(
            row -> ((Timestamp) row[4]).toLocalDateTime(),
            row -> Bme280Dto.builder()
                .temperature(toDouble(row[0]))
                .humidity(toDouble(row[1]))
                .pressure(toDouble(row[2]))
                .status((String) row[3])
                .timestamp(((Timestamp) row[4]).toLocalDateTime())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Pms5003Dto> pms5003Map = pms5003List.stream()
        .collect(Collectors.toMap(
            row -> ((Timestamp) row[4]).toLocalDateTime(),
            row -> Pms5003Dto.builder()
                .pm1(toInteger(row[0]))
                .pm2_5(toInteger(row[1]))
                .pm10(toInteger(row[2]))
                .status((String) row[3])
                .timestamp(((Timestamp) row[4]).toLocalDateTime())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Scd40Dto> scd40Map = scd40List.stream()
        .collect(Collectors.toMap(
            row -> ((Timestamp) row[4]).toLocalDateTime(),
            row -> Scd40Dto.builder()
                .co2(toInteger(row[0]))
                .temperature(toDouble(row[1]))
                .humidity(toDouble(row[2]))
                .status((String) row[3])
                .timestamp(((Timestamp) row[4]).toLocalDateTime())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Sgp40Dto> sgp40Map = sgp40List.stream()
        .collect(Collectors.toMap(
            row -> ((Timestamp) row[2]).toLocalDateTime(),
            row -> Sgp40Dto.builder()
                .vocIndex(toInteger(row[0]))
                .status((String) row[1])
                .timestamp(((Timestamp) row[2]).toLocalDateTime())
                .build(),
            (existing, replacement) -> existing
        ));

        List<SensorReadingsDto> data = IntStream.range(0, 24)
            .mapToObj(i -> {
                LocalDateTime hour = start.plusHours(i);

                return SensorReadingsDto.builder()
                    .bme280Dto(bme280Map.get(hour))
                    .pms5003Dto(pms5003Map.get(hour))
                    .scd40Dto(scd40Map.get(hour))
                    .sgp40Dto(sgp40Map.get(hour))
                    .build();
            })
            .toList();

        return SensorReadingsTimeSeriesDto.builder()
            .data(data)
            .timeRange("hourly")
            .startTime(start)
            .endTime(end)
            .build();


    }

    public SensorReadingsTimeSeriesDto getDaily(LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.atStartOfDay().plusDays(1);

        List<Object[]> bme280List = bme280Repository.getDailyAverages(start, end);
        List<Object[]> pms5003List = pms5003Repository.getDailyAverages(start, end);
        List<Object[]> scd40List = scd40Repository.getDailyAverages(start, end);
        List<Object[]> sgp40List = sgp40Repository.getDailyAverages(start, end);

        
        Map<LocalDateTime, Bme280Dto> bme280Map = bme280List.stream()
        .collect(Collectors.toMap(
            row -> ((Date) row[4]).toLocalDate().atStartOfDay(),
            row -> Bme280Dto.builder()
                .temperature(toDouble(row[0]))
                .humidity(toDouble(row[1]))
                .pressure(toDouble(row[2]))
                .status((String) row[3])
                .timestamp(((Date) row[4]).toLocalDate().atStartOfDay())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Pms5003Dto> pms5003Map = pms5003List.stream()
        .collect(Collectors.toMap(
            row -> ((Date) row[4]).toLocalDate().atStartOfDay(),
            row -> Pms5003Dto.builder()
                .pm1(toInteger(row[0]))
                .pm2_5(toInteger(row[1]))
                .pm10(toInteger(row[2]))
                .status((String) row[3])
                .timestamp(((Date) row[4]).toLocalDate().atStartOfDay())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Scd40Dto> scd40Map = scd40List.stream()
        .collect(Collectors.toMap(
            row -> ((Date) row[4]).toLocalDate().atStartOfDay(),
            row -> Scd40Dto.builder()
                .co2(toInteger(row[0]))
                .temperature(toDouble(row[1]))
                .humidity(toDouble(row[2]))
                .status((String) row[3])
                .timestamp(((Date) row[4]).toLocalDate().atStartOfDay())
                .build(),
            (existing, replacement) -> existing
        ));

        Map<LocalDateTime, Sgp40Dto> sgp40Map = sgp40List.stream()
        .collect(Collectors.toMap(
            row -> ((Date) row[2]).toLocalDate().atStartOfDay(),
            row -> Sgp40Dto.builder()
                .vocIndex(toInteger(row[0]))
                .status((String) row[1])
                .timestamp(((Date) row[2]).toLocalDate().atStartOfDay())
                .build(),
            (existing, replacement) -> existing
        ));

        List<SensorReadingsDto> data = startDate.datesUntil(endDate.plusDays(1))
                .map(date -> {
                    LocalDateTime dateTime = date.atStartOfDay();

                    return SensorReadingsDto.builder()
                        .bme280Dto(bme280Map.get(dateTime))
                        .pms5003Dto(pms5003Map.get(dateTime))
                        .scd40Dto(scd40Map.get(dateTime))
                        .sgp40Dto(sgp40Map.get(dateTime))
                        .build();
                })
                .toList();

        return SensorReadingsTimeSeriesDto.builder()
        .data(data)
        .timeRange("daily")
        .startTime(start)
        .endTime(end)
        .build();
    }

    private Double toDouble(Object obj) {
        if (obj instanceof BigDecimal){
            return ((BigDecimal) obj).doubleValue();
        } else if (obj instanceof Double) {
            return (Double) obj;
        } else {
            return null;
        }
    }

    private Integer toInteger(Object obj) {
        if (obj instanceof BigDecimal){
            return ((BigDecimal) obj).intValue();
        } else if (obj instanceof Integer) {
            return (Integer) obj;
        } else if (obj instanceof Double){
            return ((Double) obj).intValue();
        } else {
            return null;
        }
    }    
}
