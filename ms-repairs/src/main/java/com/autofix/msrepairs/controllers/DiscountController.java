package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.entities.DiscountCouponEntity;
import com.autofix.msrepairs.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
@Validated
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public List<DiscountCouponEntity> getDiscounts() {
        return discountService.getDiscounts();
    }

    @GetMapping("/quantity")
    public ResponseEntity<Integer> getDiscountQuantity(@RequestParam String brand) {
        Optional<DiscountCouponEntity> couponOpt = discountService.getCouponByBrand(brand);
        return couponOpt.map(coupon -> ResponseEntity.ok(coupon.getQuantity()))
                .orElseGet(() -> ResponseEntity.ok(0));
    }

    @PostMapping("/{repairId}/apply-coupon")
    public ResponseEntity<String> applyDiscountCoupon(@PathVariable Long repairId, @RequestParam String brand) {
        System.out.println("Received request to apply coupon for brand: " + brand + " and repairId: " + repairId);
        try {
            discountService.applyDiscountCoupon(repairId, brand);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            System.err.println("Error applying discount: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}