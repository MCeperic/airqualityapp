package hr.tvz.ceperic.airqualityapp.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorReadingsTimeSeriesDto {
    private List<SensorReadingsDto> data;
    private String timeRange;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
