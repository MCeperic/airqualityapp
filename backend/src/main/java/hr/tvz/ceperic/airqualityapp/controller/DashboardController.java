package hr.tvz.ceperic.airqualityapp.controller;

import hr.tvz.ceperic.airqualityapp.dto.SensorReadingsDto;
import hr.tvz.ceperic.airqualityapp.service.DashboardService;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
@RequestMapping("/aqa/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/latestReadings")
    public ResponseEntity<SensorReadingsDto> getLatestReadings(){
        return ResponseEntity.ok(dashboardService.getLatestReadings());
    }

}
