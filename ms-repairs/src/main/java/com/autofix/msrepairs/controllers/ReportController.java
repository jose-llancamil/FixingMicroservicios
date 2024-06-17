package com.autofix.msrepairs.controllers;

import com.autofix.msrepairs.requests.RepairTypeReportDTO;
import com.autofix.msrepairs.requests.MonthlyRepairComparisonDTO;
import com.autofix.msrepairs.services.ReportService;
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

    @GetMapping("/repairs/report/repair-type")
    public ResponseEntity<List<RepairTypeReportDTO>> getRepairTypeReport(@RequestParam int month, @RequestParam int year) {
        List<RepairTypeReportDTO> report = reportService.generateRepairTypeReport(month, year);
        return ResponseEntity.ok(report);
    }

    @GetMapping("/repairs/report/monthly-comparison")
    public ResponseEntity<List<MonthlyRepairComparisonDTO>> getMonthlyRepairComparisonReport(@RequestParam int month, @RequestParam int year) {
        List<MonthlyRepairComparisonDTO> report = reportService.generateMonthlyRepairComparisonReport(month, year);
        return ResponseEntity.ok(report);
    }
}
