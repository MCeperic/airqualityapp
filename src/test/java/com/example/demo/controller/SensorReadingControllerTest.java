package com.example.demo.controller;


import com.example.demo.model.SensorReading;
import com.example.demo.repository.SensorReadingRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SensorReadingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SensorReadingRepository sensorReadingRepository;

    @Test
    public void testGetAllReadings() throws Exception {
        SensorReading reading = new SensorReading("CO2", 40D, LocalDateTime.now());
        List<SensorReading> list = List.of(reading);

        Mockito.when(sensorReadingRepository.findAll()).thenReturn(list);

        mockMvc.perform("/aqa/readings").
    }

}
