package hr.tvz.ceperic.airqualityapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Sgp40Dto {
    private Integer vocIndex;
    private String status;
    private LocalDateTime timestamp;
}

