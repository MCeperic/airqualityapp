package hr.tvz.ceperic.airqualityapp.controller;


import hr.tvz.ceperic.airqualityapp.service.SensorReadingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SensorReadingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SensorReadingService service;

    @InjectMocks
    private SensorReadingController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testGetAllReadings() throws Exception {
        SensorReadingDTO reading1 = new SensorReadingDTO("CO2", 40D, LocalDateTime.now());
        SensorReadingDTO reading2 = new SensorReadingDTO("PM2.5", 20D, LocalDateTime.now().minusHours(2));

        List<SensorReadingDTO> readings = Arrays.asList(reading1, reading2);

        Mockito.when(service.getAllReadings()).thenReturn(readings);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(get("/aqa/readings")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(readings)));

        verify(service, times(1)).getAllReadings();

    }
    @Test
    void shouldReturnAllSensorReadings() throws Exception {
        SensorReadingDTO reading1 = new SensorReadingDTO("CO2", 40D, LocalDateTime.now());
        SensorReadingDTO reading2 = new SensorReadingDTO("PM2.5", 20D, LocalDateTime.now().minusHours(2));

        List<SensorReadingDTO> readings = Arrays.asList(reading1, reading2);

        when(service.getAllReadings()).thenReturn(readings);

        mockMvc.perform(get("/aqa/readings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
