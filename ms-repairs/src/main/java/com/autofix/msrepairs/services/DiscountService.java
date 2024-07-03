package com.autofix.msrepairs.services;

import com.autofix.msrepairs.entities.DiscountCouponEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.DiscountCouponRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {

    private final DiscountCouponRepository discountCouponRepository;
    private final RepairRepository repairRepository;

    public List<DiscountCouponEntity> getDiscounts() {
        return discountCouponRepository.findAll();
    }

    public Optional<DiscountCouponEntity> getCouponByBrand(String brand) {
        return discountCouponRepository.findByBrand(brand);
    }

    @Transactional
    public void applyDiscountCoupon(Long repairId, String brand) {
        Optional<DiscountCouponEntity> couponOpt = discountCouponRepository.findByBrand(brand);
        if (couponOpt.isPresent()) {
            DiscountCouponEntity coupon = couponOpt.get();
            if (coupon.getQuantity() > 0) {
                RepairEntity repair = repairRepository.findById(repairId)
                        .orElseThrow(() -> new RuntimeException("Repair not found"));
                if (repair.getCouponDiscount() != null && repair.getCouponDiscount() > 0) {
                    throw new RuntimeException("A coupon discount has already been applied to this repair");
                }
                double discount = coupon.getAmount();
                repair.setCouponDiscount(discount);

                coupon.setQuantity(coupon.getQuantity() - 1);
                discountCouponRepository.save(coupon);

                // Update the total discount amount
                double totalDiscountAmount = repair.getDiscountAmount() + discount;
                repair.setDiscountAmount(totalDiscountAmount);
                repairRepository.save(repair);

                // Recalculate total cost
                double totalRepairAmount = repair.getTotalRepairAmount();
                double surchargeAmount = repair.getSurchargeAmount();
                double taxAmount = repair.getTaxAmount();
                double totalCost = totalRepairAmount + surchargeAmount - totalDiscountAmount + taxAmount;
                repair.setTotalCost(totalCost);

                repairRepository.save(repair);
            } else {
                throw new RuntimeException("No more coupons available for this brand");
            }
        } else {
            throw new RuntimeException("Coupon not found for this brand");
        }
    }
}