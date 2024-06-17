package com.autofix.msrepairlist.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "repair_list")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairListEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_reparacion", nullable = false)
    private String repairType;

    @Column(name = "precio_gasolina", nullable = false)
    private double gasolinePrice;

    @Column(name = "precio_diesel", nullable = false)
    private double dieselPrice;

    @Column(name = "precio_hibrido", nullable = false)
    private double hybridPrice;

    @Column(name = "precio_electrico", nullable = false)
    private double electricPrice;
}