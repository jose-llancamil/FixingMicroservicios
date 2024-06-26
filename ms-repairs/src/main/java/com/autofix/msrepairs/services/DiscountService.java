package com.autofix.msrepairs.services;

import com.autofix.msrepairs.entities.DiscountCouponEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.DiscountCouponRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountCouponRepository discountCouponRepository;
    private final RepairRepository repairRepository;

    /**
     * Applies a discount coupon to a repair.
     * @param repairId the ID of the repair.
     * @param brand the brand associated with the discount coupon.
     */
    @Transactional
    public void applyDiscountCoupon(Long repairId, String brand) {
        // Buscar el cupón por la marca
        Optional<DiscountCouponEntity> couponOpt = discountCouponRepository.findByBrand(brand);
        if (couponOpt.isPresent()) {
            DiscountCouponEntity coupon = couponOpt.get();

            // Verificar si hay cupones disponibles
            if (coupon.getQuantity() > 0) {
                // Buscar la reparación por ID
                RepairEntity repair = repairRepository.findById(repairId)
                        .orElseThrow(() -> new RuntimeException("Repair not found"));

                // Verificar si ya se ha aplicado un descuento
                if (repair.getDiscountAmount() != null && repair.getDiscountAmount() > 0) {
                    throw new RuntimeException("A discount has already been applied to this repair");
                }

                // Aplicar el descuento
                double discountAmount = coupon.getAmount();
                repair.setDiscountAmount(discountAmount);

                // Reducir la cantidad de cupones disponibles
                coupon.setQuantity(coupon.getQuantity() - 1);
                discountCouponRepository.save(coupon);

                // Guardar los cambios en la reparación
                repairRepository.save(repair);
            } else {
                throw new RuntimeException("No more coupons available for this brand");
            }
        } else {
            throw new RuntimeException("Coupon not found for this brand");
        }
    }
}