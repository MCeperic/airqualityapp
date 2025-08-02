package hr.tvz.ceperic.airqualityapp.controller;

import hr.tvz.ceperic.airqualityapp.dto.SensorTrendDTO;
import hr.tvz.ceperic.airqualityapp.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/aqa/dashboard")
public class DashboardController {

    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

}
