package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.RepairListFeignClient;
import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.requests.PriceListDTO;
import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairDetailsRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.RepairDetailDTO;
import com.autofix.msrepairs.requests.VehicleDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RepairDetailsService {

    private final RepairDetailsRepository repairDetailsRepository;
    private final RepairRepository repairRepository;
    private final VehicleFeignClient vehicleFeignClient;
    private final RepairListFeignClient repairListFeignClient;
    private final SurchargeService surchargeService;

    public List<RepairDetailsEntity> findAllRepairDetails() {
        return repairDetailsRepository.findAll();
    }

    public Optional<RepairDetailsEntity> findRepairDetailsById(Long id) {
        return repairDetailsRepository.findById(id);
    }

    public List<RepairDetailDTO> getRepairDetailsByVehicleId(Long vehicleId) {
        VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle not found");
        }

        List<RepairEntity> repairs = repairRepository.findAllByVehicleId(vehicleId);

        return repairs.stream()
                .flatMap(repair -> {
                    List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(repair.getId());
                    return repairDetails.stream().map(detail -> {
                        RepairDetailDTO dto = new RepairDetailDTO();
                        dto.setId(detail.getId());
                        dto.setRepairId(detail.getRepairId());
                        dto.setRepairType(detail.getRepairType());
                        dto.setRepairDate(detail.getRepairDate());
                        dto.setRepairTime(detail.getRepairTime());
                        dto.setRepairAmount(detail.getRepairAmount());
                        dto.setLicensePlate(vehicle.getLicensePlate());
                        return dto;
                    });
                })
                .collect(Collectors.toList());
    }

    public RepairDetailsEntity saveRepairDetails(RepairDetailsEntity repairDetailsEntity) {
        Optional<RepairEntity> repairOpt = repairRepository.findById(repairDetailsEntity.getRepairId());
        if (repairOpt.isPresent()) {
            Long vehicleId = repairOpt.get().getVehicleId();
            VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);

            BigDecimal repairPrice = repairListFeignClient.getRepairPrice(
                    repairDetailsEntity.getRepairType(), vehicle.getEngineType()
            );
            if (repairPrice == null) {
                repairPrice = BigDecimal.valueOf(0.0);
            }
            repairDetailsEntity.setRepairAmount(repairPrice);

            RepairDetailsEntity savedDetail = repairDetailsRepository.save(repairDetailsEntity);

            updateRepairAmounts(repairDetailsEntity.getRepairId());

            return savedDetail;
        } else {
            throw new RuntimeException("Repair not found");
        }
    }

    public RepairDetailsEntity updateRepairDetails(Long id, @Valid RepairDetailsEntity repairDetails) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair details not found with id " + id);
        }

        Optional<RepairEntity> repairOpt = repairRepository.findById(repairDetails.getRepairId());
        if (repairOpt.isPresent()) {
            Long vehicleId = repairOpt.get().getVehicleId();
            VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);

            BigDecimal repairPrice = repairListFeignClient.getRepairPrice(
                    repairDetails.getRepairType(), vehicle.getEngineType()
            );
            if (repairPrice == null) {
                repairPrice = BigDecimal.valueOf(0.0);
            }
            repairDetails.setRepairAmount(repairPrice);

            repairDetails.setId(id);
            RepairDetailsEntity updatedDetail = repairDetailsRepository.save(repairDetails);

            updateRepairAmounts(repairDetails.getRepairId());

            return updatedDetail;
        } else {
            throw new RuntimeException("Repair not found");
        }
    }

    public void deleteRepairDetailById(Long id) {
        RepairDetailsEntity repairDetail = repairDetailsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Repair detail not found with id " + id));
        Long repairId = repairDetail.getRepairId();
        repairDetailsRepository.deleteById(id);
        updateRepairAmounts(repairId);
    }

    public void deleteRepairDetailsByVehicleId(Long vehicleId) {
        List<RepairEntity> repairs = repairRepository.findAllByVehicleId(vehicleId);
        if (repairs.isEmpty()) {
            throw new IllegalArgumentException("No repairs found for vehicleId " + vehicleId);
        }

        repairs.forEach(repair -> {
            List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(repair.getId());
            repairDetailsRepository.deleteAll(repairDetails);
            repairRepository.delete(repair);
        });
    }

    public void updateRepairAmounts(Long repairId) {
        List<RepairDetailsEntity> details = repairDetailsRepository.findAllByRepairId(repairId);

        double totalRepairAmount = details.stream().mapToDouble(d -> d.getRepairAmount().doubleValue()).sum();
        double surchargeAmount = details.isEmpty() ? 0 : calculateSurcharges(totalRepairAmount, repairId);
        double dayOfServiceDiscount = calculateDayOfServiceDiscount(totalRepairAmount, repairId);
        double numberOfRepairsDiscount = calculateNumberOfRepairsDiscount(totalRepairAmount, repairId);

        // Obtener la reparación actual
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();

        // Validar si ya existe un descuento por cupón y mantener su valor
        double couponDiscount = repair.getCouponDiscount() != null ? repair.getCouponDiscount() : 0.0;

        // Sumar todos los descuentos incluyendo el de cupón
        double discountAmount = dayOfServiceDiscount + numberOfRepairsDiscount + couponDiscount;

        double taxAmount = details.isEmpty() ? 0 : totalRepairAmount * 0.19;

        double totalCost = calculateTotalCost(totalRepairAmount, surchargeAmount, discountAmount, taxAmount);

        repair.setTotalRepairAmount(totalRepairAmount);
        repair.setSurchargeAmount(surchargeAmount);
        repair.setDiscountAmount(discountAmount);
        repair.setCouponDiscount(couponDiscount);
        repair.setTaxAmount(taxAmount);
        repair.setTotalCost(totalCost);

        repairRepository.save(repair);
    }

    private double calculateTotalCost(double totalRepairAmount, double surchargeAmount, double discountAmount, double taxAmount) {
        return totalRepairAmount + surchargeAmount - discountAmount + taxAmount;
    }

    private double calculateCouponDiscount(Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        return repair.getCouponDiscount() != null ? repair.getCouponDiscount() : 0.0;
    }

    private double calculateSurcharges(double totalRepairAmount, Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        Long vehicleId = repair.getVehicleId();

        double mileageSurcharge = surchargeService.calculateMileageSurcharge(vehicleId, totalRepairAmount);
        double ageSurcharge = surchargeService.calculateAgeSurcharge(vehicleId, totalRepairAmount);
        double latePickupSurcharge = surchargeService.calculateLatePickupSurcharge(repairId, totalRepairAmount);

        return mileageSurcharge + ageSurcharge + latePickupSurcharge;
    }

    private double calculateDiscounts(double totalRepairAmount, Long repairId) {
        double dayOfServiceDiscount = calculateDayOfServiceDiscount(totalRepairAmount, repairId);
        double numberOfRepairsDiscount = calculateNumberOfRepairsDiscount(totalRepairAmount, repairId);

        return dayOfServiceDiscount + numberOfRepairsDiscount;
    }

    private double calculateDayOfServiceDiscount(double totalRepairAmount, Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        LocalDate entryDate = repair.getEntryDate();
        LocalTime entryTime = repair.getEntryTime();
        DayOfWeek dayOfWeek = entryDate.getDayOfWeek();

        if ((dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.THURSDAY)
                && entryTime.isAfter(LocalTime.of(9, 0))
                && entryTime.isBefore(LocalTime.of(12, 0))) {
            return totalRepairAmount * 0.10;
        }
        return 0.0;
    }

    private double calculateNumberOfRepairsDiscount(double totalRepairAmount, Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        Long vehicleId = repair.getVehicleId();
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);
        List<RepairEntity> repairsLastYear = repairRepository.findAllByVehicleIdAndEntryDateAfter(vehicleId, oneYearAgo);

        int numRepairs = repairsLastYear.size();
        String engineType = vehicleFeignClient.getVehicleById(vehicleId).getEngineType();

        if (numRepairs <= 2) {
            return totalRepairAmount * getDiscountRate(engineType, 1);
        } else if (numRepairs <= 5) {
            return totalRepairAmount * getDiscountRate(engineType, 2);
        } else if (numRepairs <= 9) {
            return totalRepairAmount * getDiscountRate(engineType, 3);
        } else {
            return totalRepairAmount * getDiscountRate(engineType, 4);
        }
    }

    private double getDiscountRate(String engineType, int bracket) {
        switch (engineType.toLowerCase()) {
            case "gasolina":
                return switch (bracket) {
                    case 1 -> 0.05;
                    case 2 -> 0.10;
                    case 3 -> 0.15;
                    case 4 -> 0.20;
                    default -> 0.0;
                };
            case "diesel":
                return switch (bracket) {
                    case 1 -> 0.07;
                    case 2 -> 0.12;
                    case 3 -> 0.17;
                    case 4 -> 0.22;
                    default -> 0.0;
                };
            case "hibrido":
                return switch (bracket) {
                    case 1 -> 0.10;
                    case 2 -> 0.15;
                    case 3 -> 0.20;
                    case 4 -> 0.25;
                    default -> 0.0;
                };
            case "electrico":
                return switch (bracket) {
                    case 1 -> 0.08;
                    case 2 -> 0.13;
                    case 3 -> 0.18;
                    case 4 -> 0.23;
                    default -> 0.0;
                };
            default:
                return 0.0;
        }
    }
}