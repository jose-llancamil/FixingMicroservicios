package com.autofix.msrepairs.requests;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRepairComparisonDTO {
    private String month;
    private long repairCount;
    private double totalAmount;
}