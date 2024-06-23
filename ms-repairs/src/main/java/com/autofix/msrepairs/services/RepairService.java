package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairDetailsRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.VehicleDTO;
import com.autofix.msrepairs.requests.VehicleRepairHistoryDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class RepairService {

    private final RepairRepository repairRepository;
    private final RepairDetailsRepository repairDetailsRepository;
    private final VehicleFeignClient vehicleFeignClient;

    /**
     * Retrieves all repairs.
     * @return a list of all repairs.
     */
    public List<RepairEntity> findAllRepairs() {
        return repairRepository.findAll();
    }

    /**
     * Retrieves a repair by its ID.
     * @param id the ID of the repair.
     * @return an optional containing the repair if found, or empty if not.
     */
    public Optional<RepairEntity> findRepairById(Long id) {
        return repairRepository.findById(id);
    }

    /**
     * Saves a new repair.
     * @param repair the repair entity to be saved.
     * @return the saved repair entity.
     */
    public RepairEntity saveRepair(@Valid RepairEntity repair) {
        return repairRepository.save(repair);
    }

    /**
     * Updates an existing repair by its ID.
     * @param id the ID of the repair to be updated.
     * @param repair the updated repair entity.
     * @return the updated repair entity.
     */
    public RepairEntity updateRepair(Long id, @Valid RepairEntity repair) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        repair.setId(id);
        return repairRepository.save(repair);
    }

    /**
     * Deletes a repair by its ID.
     * @param id the ID of the repair to be deleted.
     */
    public void deleteRepairById(Long id) {
        if (!repairRepository.existsById(id)) {
            throw new IllegalArgumentException("Repair not found with id " + id);
        }
        List<RepairDetailsEntity> repairDetails = repairDetailsRepository.findAllByRepairId(id);
        repairDetailsRepository.deleteAll(repairDetails);
        repairRepository.deleteById(id);
    }

    /**
     * Retrieves the repair history for all vehicles.
     * @return a list of VehicleRepairHistoryDTO containing the repair history for each vehicle.
     */
    public List<VehicleRepairHistoryDTO> getVehicleRepairHistory() {
        List<RepairEntity> repairs = repairRepository.findAll();

        return repairs.stream()
                .map(repair -> {
                    VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repair.getVehicleId());
                    VehicleRepairHistoryDTO dto = new VehicleRepairHistoryDTO();
                    dto.setLicensePlate(vehicle.getLicensePlate());
                    dto.setBrand(vehicle.getBrand());
                    dto.setModel(vehicle.getModel());
                    dto.setType(vehicle.getType());
                    dto.setYearOfManufacture(vehicle.getYearOfManufacture());
                    dto.setEngineType(vehicle.getEngineType());
                    dto.setEntryDate(repair.getEntryDate());
                    dto.setEntryTime(repair.getEntryTime());
                    dto.setTotalRepairAmount(repair.getTotalRepairAmount());
                    dto.setSurchargeAmount(repair.getSurchargeAmount());
                    dto.setDiscountAmount(repair.getDiscountAmount());
                    dto.setTaxAmount(repair.getTaxAmount());
                    dto.setTotalCost(repair.getTotalCost());
                    dto.setExitDate(repair.getExitDate());
                    dto.setExitTime(repair.getExitTime());
                    dto.setPickupDate(repair.getPickUpDate());
                    dto.setPickupTime(repair.getPickUpTime());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}