package hr.tvz.ceperic.airqualityapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Scd40Dto {
    private Integer co2;
    private Double temperature;
    private Double humidity;
    private String status;
    private LocalDateTime timestamp;
}
