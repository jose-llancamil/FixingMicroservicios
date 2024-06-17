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
    private long hatchbackCount;
    private long suvCount;
    private long pickupCount;
    private long vanCount;
    private double totalAmount;
}