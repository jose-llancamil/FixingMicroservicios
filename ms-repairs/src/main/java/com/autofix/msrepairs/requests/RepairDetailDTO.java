package com.autofix.msrepairs.requests;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RepairDetailDTO {
    private Long id;
    private Long repairId;
    private String repairType;
    private LocalDate repairDate;
    private LocalTime repairTime;
    private BigDecimal repairAmount;
    private String licensePlate;
}