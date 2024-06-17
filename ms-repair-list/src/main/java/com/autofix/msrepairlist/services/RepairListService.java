package com.autofix.msrepairlist.services;

import com.autofix.msrepairlist.entities.RepairListEntity;
import com.autofix.msrepairlist.repositories.RepairListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepairListService {

    @Autowired
    private RepairListRepository repairListRepository;

    public List<RepairListEntity> getAllRepairs() {
        return repairListRepository.findAll();
    }

    public Optional<RepairListEntity> getRepairById(Long id) {
        return repairListRepository.findById(id);
    }

    public RepairListEntity createRepair(RepairListEntity repairListEntity) {
        return repairListRepository.save(repairListEntity);
    }

    public RepairListEntity updateRepair(Long id, RepairListEntity repairListEntity) {
        if (repairListRepository.existsById(id)) {
            repairListEntity.setId(id);
            return repairListRepository.save(repairListEntity);
        } else {
            return null;
        }
    }

    public boolean deleteRepair(Long id) {
        if (repairListRepository.existsById(id)) {
            repairListRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public List<String> getAllRepairTypes() {
        return repairListRepository.findAll().stream()
                .map(RepairListEntity::getRepairType)
                .distinct()
                .collect(Collectors.toList());
    }

    public BigDecimal getRepairPrice(String repairType, String engineType) {
        Optional<RepairListEntity> repairOpt = repairListRepository.findByRepairType(repairType);
        if (repairOpt.isPresent()) {
            RepairListEntity repair = repairOpt.get();
            switch (engineType.toLowerCase()) {
                case "gasolina":
                    return BigDecimal.valueOf(repair.getGasolinePrice());
                case "diésel":
                    return BigDecimal.valueOf(repair.getDieselPrice());
                case "híbrido":
                    return BigDecimal.valueOf(repair.getHybridPrice());
                case "eléctrico":
                    return BigDecimal.valueOf(repair.getElectricPrice());
                default:
                    return null;
            }
        }
        return null;
    }
}