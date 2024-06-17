package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.services.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/discounts")
@RequiredArgsConstructor
@Validated
public class DiscountController {

    private final DiscountService discountService;

    /**
     * Applies a discount coupon to a repair.
     * @param repairId the ID of the repair.
     * @param brand the brand associated with the discount coupon.
     * @return a response indicating the result of the operation.
     */
    @PostMapping("/{repairId}/apply-coupon")
    public ResponseEntity<String> applyDiscountCoupon(@PathVariable Long repairId, @RequestParam String brand) {
        try {
            discountService.applyDiscountCoupon(repairId, brand);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}