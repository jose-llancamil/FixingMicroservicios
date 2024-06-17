package com.autofix.msrepairs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "repairs")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vehiculo_id", nullable = false)
    private Long vehicleId;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate entryDate;

    @Column(name = "hora_ingreso", nullable = false)
    private LocalTime entryTime;

    @Column(name = "monto_total_reparaciones")
    private Double totalRepairAmount;

    @Column(name = "monto_recargos")
    private Double surchargeAmount;

    @Column(name = "monto_descuentos")
    private Double discountAmount;

    @Column(name = "monto_iva")
    private Double taxAmount;

    @Column(name = "costo_total")
    private Double totalCost;

    @Column(name = "fecha_salida", nullable = false)
    private LocalDate exitDate;

    @Column(name = "hora_salida", nullable = false)
    private LocalTime exitTime;

    @Column(name = "fecha_retiro", nullable = false)
    private LocalDate pickUpDate;

    @Column(name = "hora_retiro", nullable = false)
    private LocalTime pickUpTime;
}