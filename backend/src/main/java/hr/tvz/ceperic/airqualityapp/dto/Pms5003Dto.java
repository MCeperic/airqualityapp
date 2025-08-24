package hr.tvz.ceperic.airqualityapp.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Pms5003Dto {
    private Integer pm1;
    private Integer pm2_5;
    private Integer pm10;
    private String status;
    private LocalDateTime timestamp;
}
