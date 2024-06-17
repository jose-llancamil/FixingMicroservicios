package com.autofix.msrepairs.requests;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleRepairHistoryDTO {
    private String licensePlate;
    private String brand;
    private String model;
    private String type;
    private int yearOfManufacture;
    private String engineType;
    private LocalDate entryDate;
    private LocalTime entryTime;
    private double totalRepairAmount;
    private double surchargeAmount;
    private double discountAmount;
    private double taxAmount;
    private double totalCost;
    private LocalDate exitDate;
    private LocalTime exitTime;
    private LocalDate pickupDate;
    private LocalTime pickupTime;
}
