package com.autofix.msrepairs.repositories;

import com.autofix.msrepairs.entities.DiscountCouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscountCouponRepository extends JpaRepository<DiscountCouponEntity, Long> {
    Optional<DiscountCouponEntity> findByBrand(String brand);
}