package hr.tvz.ceperic.airqualityapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SCD40_reading")
public class Scd40Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer co2;

    private Double temperature;

    private Double humidity;

    private String status;

    private LocalDateTime timestamp;
}
