package com.autofix.msrepairs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "repair_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "repair_id", nullable = false)
    private Long repairId;

    @Column(name = "tipo_reparacion", nullable = false)
    private String repairType;

    @Column(name = "fecha_reparacion", nullable = false)
    private LocalDate repairDate;

    @Column(name = "hora_reparacion", nullable = false)
    private LocalTime repairTime;

    @Column(name = "monto_reparacion")
    private BigDecimal repairAmount;
}