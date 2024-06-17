package com.autofix.msvehicles.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "vehicles")
@Data
@Getter
@Setter
public class VehicleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patente", nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "marca", nullable = false)
    private String brand;

    @Column(name = "modelo", nullable = false)
    private String model;

    @Column(name = "tipo", nullable = false)
    private String type;

    @Column(name = "anio_fabricacion", nullable = false)
    private int yearOfManufacture;

    @Column(name = "tipo_motor", nullable = false)
    private String engineType;

    @Column(name = "numero_asientos", nullable = false)
    private int numberOfSeats;

    @Column(name = "kilometraje", nullable = false)
    private  int mileage;
}