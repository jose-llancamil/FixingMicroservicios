package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.requests.RepairDetailDTO;
import com.autofix.msrepairs.requests.VehicleRepairHistoryDTO;
import com.autofix.msrepairs.services.RepairDetailsService;
import com.autofix.msrepairs.services.RepairService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/repairs")
@RequiredArgsConstructor
@Validated
public class RepairController {

    private final RepairService repairService;
    private final RepairDetailsService repairDetailsService;

    // Repair Methods

    @GetMapping
    public List<RepairEntity> getAllRepairs() {
        return repairService.findAllRepairs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RepairEntity> getRepairById(@PathVariable Long id) {
        return repairService.findRepairById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RepairEntity> createRepair(@RequestBody @Valid RepairEntity repair) {
        RepairEntity createdRepair = repairService.saveRepair(repair);
        return ResponseEntity.ok(createdRepair);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RepairEntity> updateRepair(@PathVariable Long id, @RequestBody @Valid RepairEntity repair) {
        try {
            RepairEntity updatedRepair = repairService.updateRepair(id, repair);
            return ResponseEntity.ok(updatedRepair);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRepair(@PathVariable Long id) {
        try {
            repairService.deleteRepairById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Repair Details Methods

    @GetMapping("/details")
    public List<RepairDetailsEntity> getAllRepairDetails() {
        return repairDetailsService.findAllRepairDetails();
    }

    @GetMapping("/details/{id}")
    public ResponseEntity<RepairDetailsEntity> getRepairDetailsById(@PathVariable Long id) {
        return repairDetailsService.findRepairDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/details/vehicle/{vehicleId}")
    public List<RepairDetailDTO> getRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        return repairDetailsService.getRepairDetailsByVehicleId(vehicleId);
    }

    @PostMapping("/details")
    public ResponseEntity<List<RepairDetailsEntity>> createRepairDetails(@RequestBody @Valid List<RepairDetailsEntity> repairDetailsList) {
        List<RepairDetailsEntity> createdRepairDetailsList = repairDetailsList.stream()
                .map(repairDetailsService::saveRepairDetails)
                .collect(Collectors.toList());
        return ResponseEntity.ok(createdRepairDetailsList);
    }

    @PutMapping("/details/{id}")
    public ResponseEntity<RepairDetailsEntity> updateRepairDetails(@PathVariable Long id, @RequestBody @Valid RepairDetailsEntity repairDetails) {
        try {
            RepairDetailsEntity updatedRepairDetails = repairDetailsService.updateRepairDetails(id, repairDetails);
            return ResponseEntity.ok(updatedRepairDetails);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/{id}")
    public ResponseEntity<Void> deleteRepairDetailById(@PathVariable Long id) {
        try {
            repairDetailsService.deleteRepairDetailById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/details/vehicle/{vehicleId}")
    public ResponseEntity<Void> deleteRepairDetailsByVehicleId(@PathVariable Long vehicleId) {
        try {
            repairDetailsService.deleteRepairDetailsByVehicleId(vehicleId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/history")
    public ResponseEntity<List<VehicleRepairHistoryDTO>> getVehicleRepairHistory() {
        List<VehicleRepairHistoryDTO> history = repairService.getVehicleRepairHistory();
        return ResponseEntity.ok(history);
    }
}