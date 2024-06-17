package com.autofix.msrepairs.repositories;

import com.autofix.msrepairs.entities.RepairDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairDetailsRepository extends JpaRepository<RepairDetailsEntity, Long> {
    List<RepairDetailsEntity> findAllByRepairId(Long repairId);
}