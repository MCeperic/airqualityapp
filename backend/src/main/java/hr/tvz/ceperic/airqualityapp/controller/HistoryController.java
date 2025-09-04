package hr.tvz.ceperic.airqualityapp.controller;

import hr.tvz.ceperic.airqualityapp.dto.SensorReadingsTimeSeriesDto;
import hr.tvz.ceperic.airqualityapp.service.HistoryService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/aqa/history")
@Slf4j
public class HistoryController {

    private HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/minute")
    public ResponseEntity<SensorReadingsTimeSeriesDto> getEveryMinute(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam Integer hour) {

        if(hour < 0 || hour > 23){
            return ResponseEntity.badRequest().build();
        }
        
        log.info("Fetching every minute data for date: {} hour: {}", date, hour);
        SensorReadingsTimeSeriesDto sensorReadingsTimeSeriesDto = historyService.getEveryMinute(date, hour);
        return ResponseEntity.ok(sensorReadingsTimeSeriesDto);
    }

    @GetMapping("/hourly")
    public ResponseEntity<SensorReadingsTimeSeriesDto> getHourly(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        log.info("Fetching hourly data for date: {}", date);
        SensorReadingsTimeSeriesDto sensorReadingsTimeSeriesDto = historyService.getHourly(date);
        return ResponseEntity.ok(sensorReadingsTimeSeriesDto);
    }

    @GetMapping("/daily")
    public ResponseEntity<SensorReadingsTimeSeriesDto> getDaily(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            return ResponseEntity.badRequest().build();
        }

        log.info("Fetching daily data from date: {} to {}", startDate, endDate);
        SensorReadingsTimeSeriesDto sensorReadingsTimeSeriesDto = historyService.getDaily(startDate, endDate);
        return ResponseEntity.ok(sensorReadingsTimeSeriesDto);
    }
    
    

}
