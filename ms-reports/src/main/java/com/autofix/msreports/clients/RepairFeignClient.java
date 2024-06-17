package com.autofix.msreports.clients;

import com.autofix.msreports.requests.RepairTypeReportDTO;
import com.autofix.msreports.requests.MonthlyRepairComparisonDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "ms-repairs")
public interface RepairFeignClient {

    @GetMapping("/repairs/report/repair-type")
    List<RepairTypeReportDTO> getRepairTypeReport(@RequestParam int month, @RequestParam int year);

    @GetMapping("/repairs/report/monthly-comparison")
    List<MonthlyRepairComparisonDTO> getMonthlyRepairComparisonReport(@RequestParam int month, @RequestParam int year);
}

