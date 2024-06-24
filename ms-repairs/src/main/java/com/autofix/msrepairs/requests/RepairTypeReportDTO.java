package com.autofix.msrepairs.requests;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RepairTypeReportDTO {
    private String repairType;
    private long sedanCount;
    private double sedanTotalAmount;
    private long hatchbackCount;
    private double hatchbackTotalAmount;
    private long suvCount;
    private double suvTotalAmount;
    private long pickupCount;
    private double pickupTotalAmount;
    private long vanCount;
    private double vanTotalAmount;
    private double totalAmount;
    private long totalCount;
}