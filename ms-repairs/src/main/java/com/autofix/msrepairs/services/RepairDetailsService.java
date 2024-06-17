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
    private final DiscountService discountService;
    private final SurchargeService surchargeService;

    /**
     * Retrieves all repair details.
     * @return a list of all repair details.
     */
    public List<RepairDetailsEntity> findAllRepairDetails() {
        return repairDetailsRepository.findAll();
    }

    /**
     * Retrieves repair details by its ID.
     * @param id the ID of the repair details.
     * @return an optional containing the repair details if found, or empty if not.
     */
    public Optional<RepairDetailsEntity> findRepairDetailsById(Long id) {
        return repairDetailsRepository.findById(id);
    }

    /**
     * Retrieves repair details by vehicle ID.
     * @param vehicleId the ID of the vehicle.
     * @return a list of repair details for the specified vehicle.
     */
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


    /**
     * Saves new repair details and updates the related repair amounts.
     * @param repairDetailsEntity the repair details entity to be saved.
     * @return the saved repair details entity.
     */
    public RepairDetailsEntity saveRepairDetails(RepairDetailsEntity repairDetailsEntity) {
        Optional<RepairEntity> repairOpt = repairRepository.findById(repairDetailsEntity.getRepairId());
        if (repairOpt.isPresent()) {
            Long vehicleId = repairOpt.get().getVehicleId();
            VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);

            BigDecimal repairPrice = repairListFeignClient.getRepairPrice(
                    repairDetailsEntity.getRepairType(), vehicle.getEngineType()
            );
            repairDetailsEntity.setRepairAmount(repairPrice);

            RepairDetailsEntity savedDetail = repairDetailsRepository.save(repairDetailsEntity);

            updateRepairAmounts(repairDetailsEntity.getRepairId());

            return savedDetail;
        } else {
            throw new RuntimeException("Repair not found");
        }
    }

//    /**
//     * Saves new repair details and updates the related repair amounts.
//     * @param repairDetailsEntity the repair details entity to be saved.
//     * @return the saved repair details entity.
//     */
//    public RepairDetailsEntity saveRepairDetails(RepairDetailsEntity repairDetailsEntity) {
//        Optional<RepairEntity> repairOpt = repairRepository.findById(repairDetailsEntity.getRepairId());
//        if (repairOpt.isPresent()) {
//            Long vehicleId = repairOpt.get().getVehicleId();
//            VehicleDTO vehicle = vehicleFeignClient.getVehicleById(vehicleId);
//
//            List<PriceListDTO> priceList = repairListFeignClient.getAllRepairs();
//            PriceListDTO matchingPrice = priceList.stream()
//                    .filter(price -> price.getRepairType().equals(repairDetailsEntity.getRepairType()))
//                    .findFirst()
//                    .orElseThrow(() -> new RuntimeException("Repair type not found in price list"));
//
//            BigDecimal repairPrice = getRepairPriceByEngineType(matchingPrice, vehicle.getEngineType());
//            repairDetailsEntity.setRepairAmount(repairPrice);
//
//            RepairDetailsEntity savedDetail = repairDetailsRepository.save(repairDetailsEntity);
//
//            updateRepairAmounts(repairDetailsEntity.getRepairId());
//
//            return savedDetail;
//        } else {
//            throw new RuntimeException("Repair not found");
//        }
//    }
//
//    private BigDecimal getRepairPriceByEngineType(PriceListDTO priceList, String engineType) {
//        switch (engineType.toLowerCase()) {
//            case "gasolina":
//                return BigDecimal.valueOf(priceList.getGasolinePrice());
//            case "diésel":
//                return BigDecimal.valueOf(priceList.getDieselPrice());
//            case "híbrido":
//                return BigDecimal.valueOf(priceList.getHybridPrice());
//            case "eléctrico":
//                return BigDecimal.valueOf(priceList.getElectricPrice());
//            default:
//                throw new IllegalArgumentException("Invalid engine type: " + engineType);
//        }
//    }

    /**
     * Updates an existing repair details by its ID.
     * @param id the ID of the repair details to be updated.
     * @param repairDetails the updated repair details entity.
     * @return the updated repair details entity.
     */
    public RepairDetailsEntity updateRepairDetails(Long id, @Valid RepairDetailsEntity repairDetails) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair details not found with id " + id);
        }
        repairDetails.setId(id);
        return repairDetailsRepository.save(repairDetails);
    }

    /**
     * Deletes a repair detail by its ID.
     * @param id the ID of the repair detail to be deleted.
     */
    public void deleteRepairDetailById(Long id) {
        if (!repairDetailsRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair detail not found with id " + id);
        }
        repairDetailsRepository.deleteById(id);
    }

    /**
     * Deletes all repair details and associated repairs for a specific vehicle ID.
     * @param vehicleId the ID of the vehicle.
     */
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

    /**
     * Updates the repair amounts (total cost, surcharge, discount, tax) for a given repair.
     * @param repairId the ID of the repair.
     */
    private void updateRepairAmounts(Long repairId) {
        List<RepairDetailsEntity> details = repairDetailsRepository.findAllByRepairId(repairId);

        double totalRepairAmount = details.stream().mapToDouble(d -> d.getRepairAmount().doubleValue()).sum();
        double surchargeAmount = calculateSurcharges(totalRepairAmount, repairId);
        double discountAmount = calculateDiscounts(totalRepairAmount, repairId);
        double taxAmount = totalRepairAmount * 0.19;
        double totalCost = totalRepairAmount + surchargeAmount - discountAmount + taxAmount;

        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        repair.setTotalRepairAmount(totalRepairAmount);
        repair.setSurchargeAmount(surchargeAmount);
        repair.setDiscountAmount(discountAmount);
        repair.setTaxAmount(taxAmount);
        repair.setTotalCost(totalCost);

        repairRepository.save(repair);
    }

    /**
     * Calculates the total surcharges for a repair.
     * @param totalRepairAmount the total repair amount.
     * @param repairId the ID of the repair.
     * @return the total surcharge amount.
     */
    private double calculateSurcharges(double totalRepairAmount, Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        Long vehicleId = repair.getVehicleId();

        double mileageSurcharge = surchargeService.calculateMileageSurcharge(vehicleId, totalRepairAmount);
        double ageSurcharge = surchargeService.calculateAgeSurcharge(vehicleId, totalRepairAmount);
        double latePickupSurcharge = surchargeService.calculateLatePickupSurcharge(repairId, totalRepairAmount);

        return mileageSurcharge + ageSurcharge + latePickupSurcharge;
    }

    /**
     * Calculates the total discounts for a repair.
     * @param totalRepairAmount the total repair amount.
     * @param repairId the ID of the repair.
     * @return the total discount amount.
     */
    private double calculateDiscounts(double totalRepairAmount, Long repairId) {
        double couponDiscount = calculateCouponDiscount(repairId);
        double dayOfServiceDiscount = calculateDayOfServiceDiscount(totalRepairAmount, repairId);
        double numberOfRepairsDiscount = calculateNumberOfRepairsDiscount(totalRepairAmount, repairId);

        return couponDiscount + dayOfServiceDiscount + numberOfRepairsDiscount;
    }

    /**
     * Calculates the coupon discount for a repair.
     * @param repairId the ID of the repair.
     * @return the coupon discount amount.
     */
    private double calculateCouponDiscount(Long repairId) {
        RepairEntity repair = repairRepository.findById(repairId).orElseThrow();
        double couponDiscount = 0.0;
        if (repair.getDiscountAmount() != null && repair.getDiscountAmount() > 0) {
            couponDiscount = repair.getDiscountAmount();
        } else {
            String vehicleBrand = vehicleFeignClient.getVehicleById(repair.getVehicleId()).getBrand();
            try {
                discountService.applyDiscountCoupon(repairId, vehicleBrand);
                repair = repairRepository.findById(repairId).orElseThrow();
                couponDiscount = repair.getDiscountAmount();
            } catch (RuntimeException e) {
                // Handle the case where the discount cannot be applied
            }
        }
        return couponDiscount;
    }

    /**
     * Calculates the day of service discount for a repair.
     * @param totalRepairAmount the total repair amount.
     * @param repairId the ID of the repair.
     * @return the day of service discount amount.
     */
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

    /**
     * Calculates the number of repairs discount for a repair.
     * @param totalRepairAmount the total repair amount.
     * @param repairId the ID of the repair.
     * @return the number of repairs discount amount.
     */
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

    /**
     * Retrieves the discount rate based on engine type and bracket.
     * @param engineType the type of engine.
     * @param bracket the discount bracket.
     * @return the discount rate.
     */
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