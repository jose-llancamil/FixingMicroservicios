package com.autofix.msrepairs.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discount_coupons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class DiscountCouponEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marca", nullable = false)
    private String brand;

    @Column(name = "monto", nullable = false)
    private Double amount;

    @Column(name = "cantidad", nullable = false)
    private Integer quantity;
}