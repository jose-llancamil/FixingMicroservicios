package com.autofix.msrepairs.requests;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonthlyRepairComparisonDTO {
    private String repairType;
    private int repairsCountPerMonth1;
    private double repairsAmountPerMonth1;
    private double variationPerMonth1;
    private double variationAmountPerMonth1;
    private int repairsCountPerMonth2;
    private double repairsAmountPerMonth2;
    private double variationPerMonth2;
    private double variationAmountPerMonth2;
    private int repairsCountPerMonth3;
    private double repairsAmountPerMonth3;
}