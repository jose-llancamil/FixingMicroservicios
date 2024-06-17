package com.autofix.msreports.services;

import com.autofix.msreports.clients.RepairFeignClient;
import com.autofix.msreports.requests.RepairTypeReportDTO;
import com.autofix.msreports.requests.MonthlyRepairComparisonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RepairFeignClient repairFeignClient;

    public List<RepairTypeReportDTO> getRepairTypeReport(int month, int year) {
        return repairFeignClient.getRepairTypeReport(month, year);
    }

    public List<MonthlyRepairComparisonDTO> getMonthlyRepairComparisonReport(int month, int year) {
        return repairFeignClient.getMonthlyRepairComparisonReport(month, year);
    }
}
