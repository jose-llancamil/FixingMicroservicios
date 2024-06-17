package com.autofix.msreports.controllers;

import com.autofix.msreports.requests.RepairTypeReportDTO;
import com.autofix.msreports.requests.MonthlyRepairComparisonDTO;
import com.autofix.msreports.services.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/reports/repair-type")
    public ResponseEntity<List<RepairTypeReportDTO>> getRepairTypeReport(@RequestParam int month, @RequestParam int year) {
        List<RepairTypeReportDTO> report = reportService.getRepairTypeReport(month, year);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/reports/monthly-comparison")
    public ResponseEntity<List<MonthlyRepairComparisonDTO>> getMonthlyRepairComparisonReport(@RequestParam int month, @RequestParam int year) {
        List<MonthlyRepairComparisonDTO> report = reportService.getMonthlyRepairComparisonReport(month, year);
        return ResponseEntity.ok(report);
    }
}
