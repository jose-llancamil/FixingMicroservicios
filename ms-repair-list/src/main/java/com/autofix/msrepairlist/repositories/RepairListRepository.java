package com.autofix.msrepairlist.repositories;

import com.autofix.msrepairlist.entities.RepairListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepairListRepository extends JpaRepository<RepairListEntity, Long> {
    Optional<RepairListEntity> findByRepairType(String repairType);
}
