package hr.tvz.ceperic.airqualityapp.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PMS5003_reading")
public class Pms5003Reading {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pm1_0")
    private Integer pm1;
    
    private Integer pm2_5;

    private Integer pm10;
    
    private String status;

    private LocalDateTime timestamp;
}

