package hr.tvz.ceperic.airqualityapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingsDto {
    private Bme280Dto bme280Dto;
    private Pms5003Dto pms5003Dto;
    private Scd40Dto scd40Dto;
    private Sgp40Dto sgp40Dto;
}
