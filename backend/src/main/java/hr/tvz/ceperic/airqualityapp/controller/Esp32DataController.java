package hr.tvz.ceperic.airqualityapp.controller;

import hr.tvz.ceperic.airqualityapp.service.Esp32DataProcessingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class Esp32DataController {
    
    private final Esp32DataProcessingService esp32DataProcessingService;

    @PostMapping("/sensor-data")
    public ResponseEntity<String> recieveSensorData(@RequestBody String jsonData){
        esp32DataProcessingService.recieveAndProcessData(jsonData);
        return ResponseEntity.ok("Data recieved!");
    }
}
