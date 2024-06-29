package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class SurchargeService {

    private final VehicleFeignClient vehicleFeignClient;
    private final RepairRepository repairRepository;

    public double calculateMileageSurcharge(Long vehicleId, double totalRepairCost) {
        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);
        int mileage = vehicle.getMileage();
        String vehicleType = vehicle.getType().toLowerCase();

        double surcharge = 0.0;
        if (mileage > 5000 && mileage <= 12000) {
            surcharge = totalRepairCost * getMileageSurchargeRate(vehicleType, 1);
        } else if (mileage > 12000 && mileage <= 25000) {
            surcharge = totalRepairCost * getMileageSurchargeRate(vehicleType, 2);
        } else if (mileage > 25000 && mileage <= 40000) {
            surcharge = totalRepairCost * getMileageSurchargeRate(vehicleType, 3);
        } else if (mileage > 40000) {
            surcharge = totalRepairCost * getMileageSurchargeRate(vehicleType, 4);
        }
        return surcharge;
    }

    private double getMileageSurchargeRate(String vehicleType, int bracket) {
        switch (vehicleType) {
            case "sedan":
            case "hatchback":
                return switch (bracket) {
                    case 1 -> 0.03;
                    case 2 -> 0.07;
                    case 3 -> 0.12;
                    case 4 -> 0.20;
                    default -> 0.0;
                };
            case "suv":
            case "pickup":
            case "furgoneta":
                return switch (bracket) {
                    case 1 -> 0.05;
                    case 2 -> 0.09;
                    case 3 -> 0.12;
                    case 4 -> 0.20;
                    default -> 0.0;
                };
            default:
                return 0.0;
        }
    }

    public double calculateAgeSurcharge(Long vehicleId, double totalRepairCost) {
        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);
        int vehicleAge = LocalDate.now().getYear() - vehicle.getYearOfManufacture();

        double surcharge = 0.0;
        if (vehicleAge > 5 && vehicleAge <= 10) {
            surcharge = totalRepairCost * getAgeSurchargeRate(vehicle.getType().toLowerCase(), 1);
        } else if (vehicleAge > 10 && vehicleAge <= 15) {
            surcharge = totalRepairCost * getAgeSurchargeRate(vehicle.getType().toLowerCase(), 2);
        } else if (vehicleAge > 15) {
            surcharge = totalRepairCost * getAgeSurchargeRate(vehicle.getType().toLowerCase(), 3);
        }
        return surcharge;
    }

    private double getAgeSurchargeRate(String vehicleType, int bracket) {
        switch (vehicleType) {
            case "sedan":
            case "hatchback":
                return switch (bracket) {
                    case 1 -> 0.05;
                    case 2 -> 0.09;
                    case 3 -> 0.15;
                    default -> 0.0;
                };
            case "suv":
            case "pickup":
            case "furgoneta":
                return switch (bracket) {
                    case 1 -> 0.07;
                    case 2 -> 0.11;
                    case 3 -> 0.20;
                    default -> 0.0;
                };
            default:
                return 0.0;
        }
    }

    public double calculateLatePickupSurcharge(Long repairId, double totalRepairCost) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        LocalDate readyDate = repair.getExitDate();
        LocalDate pickupDate = repair.getPickUpDate();

        long daysLate = ChronoUnit.DAYS.between(readyDate, pickupDate);

        return daysLate > 0 ? totalRepairCost * 0.05 * daysLate : 0.0;
    }
}
