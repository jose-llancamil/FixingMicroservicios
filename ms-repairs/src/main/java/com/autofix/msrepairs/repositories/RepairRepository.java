package com.autofix.msrepairs.repositories;

import com.autofix.msrepairs.entities.RepairEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<RepairEntity, Long> {
    List<RepairEntity> findAllByVehicleId(Long vehicleId);
    List<RepairEntity> findAllByVehicleIdAndEntryDateAfter(Long vehicleId, LocalDate entryDate);

    @Query("SELECT r FROM RepairEntity r WHERE MONTH(r.entryDate) = :month AND YEAR(r.entryDate) = :year")
    List<RepairEntity> findAllByMonthAndYear(int month, int year);
}

