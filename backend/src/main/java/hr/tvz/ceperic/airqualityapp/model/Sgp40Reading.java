package hr.tvz.ceperic.airqualityapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SGP40_reading")
public class Sgp40Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "voc_index")
    private Integer vocIndex;

    private String status;

    private LocalDateTime timestamp;
}
