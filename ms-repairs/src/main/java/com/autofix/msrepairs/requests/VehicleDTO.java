package com.autofix.msrepairs.requests;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehicleDTO {
    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String type;
    private int yearOfManufacture;
    private String engineType;
    private int numberOfSeats;
    private int mileage;
}