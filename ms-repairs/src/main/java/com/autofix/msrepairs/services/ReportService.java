package com.autofix.msrepairs.services;

import com.autofix.msrepairs.clients.VehicleFeignClient;
import com.autofix.msrepairs.requests.RepairTypeReportDTO;
import com.autofix.msrepairs.requests.MonthlyRepairComparisonDTO;
import com.autofix.msrepairs.entities.RepairDetailsEntity;
import com.autofix.msrepairs.entities.RepairEntity;
import com.autofix.msrepairs.repositories.RepairDetailsRepository;
import com.autofix.msrepairs.repositories.RepairRepository;
import com.autofix.msrepairs.requests.VehicleDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final RepairRepository repairRepository;
    private final RepairDetailsRepository repairDetailsRepository;
    private final VehicleFeignClient vehicleFeignClient;

    public List<RepairTypeReportDTO> generateRepairTypeReport(int month, int year) {
        List<RepairEntity> repairs = repairRepository.findAllByMonthAndYear(month, year);

        Map<String, RepairTypeReportDTO> reportMap = repairs.stream()
                .flatMap(repair -> repairDetailsRepository.findAllByRepairId(repair.getId()).stream())
                .collect(Collectors.groupingBy(
                        RepairDetailsEntity::getRepairType,
                        Collectors.collectingAndThen(Collectors.toList(), repairDetailsList -> {
                            RepairTypeReportDTO dto = new RepairTypeReportDTO();
                            dto.setRepairType(repairDetailsList.get(0).getRepairType());

                            long sedanCount = repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("sedan");
                            }).count();
                            dto.setSedanCount(sedanCount);
                            dto.setSedanTotalAmount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("sedan");
                            }).mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());

                            long hatchbackCount = repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("hatchback");
                            }).count();
                            dto.setHatchbackCount(hatchbackCount);
                            dto.setHatchbackTotalAmount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("hatchback");
                            }).mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());

                            long suvCount = repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("suv");
                            }).count();
                            dto.setSuvCount(suvCount);
                            dto.setSuvTotalAmount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("suv");
                            }).mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());

                            long pickupCount = repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("pickup");
                            }).count();
                            dto.setPickupCount(pickupCount);
                            dto.setPickupTotalAmount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("pickup");
                            }).mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());

                            long vanCount = repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("furgoneta");
                            }).count();
                            dto.setVanCount(vanCount);
                            dto.setVanTotalAmount(repairDetailsList.stream().filter(rd -> {
                                VehicleDTO vehicle = vehicleFeignClient.getVehicleById(repairRepository.findById(rd.getRepairId()).get().getVehicleId());
                                return vehicle.getType().equalsIgnoreCase("furgoneta");
                            }).mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum());

                            dto.setTotalAmount(
                                            dto.getSedanTotalAmount() +
                                            dto.getHatchbackTotalAmount() +
                                            dto.getSuvTotalAmount() +
                                            dto.getPickupTotalAmount() +
                                            dto.getVanTotalAmount()
                            );

                            dto.setTotalCount(
                                            sedanCount +
                                            hatchbackCount +
                                            suvCount +
                                            pickupCount +
                                            vanCount
                            );

                            return dto;
                        })
                ));

        return reportMap.values().stream().collect(Collectors.toList());
    }

    public List<MonthlyRepairComparisonDTO> generateMonthlyRepairComparisonReport(int month, int year) {
        LocalDate date = LocalDate.of(year, month, 1);

        Map<String, MonthlyRepairComparisonDTO> dataMonth1 = getMonthlyRepairData(date.minusMonths(2), null);
        Map<String, MonthlyRepairComparisonDTO> dataMonth2 = getMonthlyRepairData(date.minusMonths(1), dataMonth1);
        Map<String, MonthlyRepairComparisonDTO> dataMonth3 = getMonthlyRepairData(date, dataMonth2);

        List<MonthlyRepairComparisonDTO> report = new ArrayList<>(dataMonth3.values());

        for (MonthlyRepairComparisonDTO dto : report) {
            MonthlyRepairComparisonDTO month1Dto = dataMonth1.get(dto.getRepairType());
            if (month1Dto != null) {
                dto.setRepairsCountPerMonth1(month1Dto.getRepairsCountPerMonth3());
                dto.setRepairsAmountPerMonth1(month1Dto.getRepairsAmountPerMonth3());

                if (month1Dto.getRepairsCountPerMonth3() != 0) {
                    dto.setVariationPerMonth1((dto.getRepairsCountPerMonth2() - month1Dto.getRepairsCountPerMonth3()) / (double) month1Dto.getRepairsCountPerMonth3() * 100);
                } else {
                    dto.setVariationPerMonth1(dto.getRepairsCountPerMonth2() * 100);
                }

                if (month1Dto.getRepairsAmountPerMonth3() != 0) {
                    dto.setVariationAmountPerMonth1((dto.getRepairsAmountPerMonth2() - month1Dto.getRepairsAmountPerMonth3()) / month1Dto.getRepairsAmountPerMonth3() * 100);
                } else {
                    dto.setVariationAmountPerMonth1(dto.getRepairsAmountPerMonth2() * 100);
                }
            }
        }

        return report;
    }

    private Map<String, MonthlyRepairComparisonDTO> getMonthlyRepairData(LocalDate date, Map<String, MonthlyRepairComparisonDTO> previousMonthData) {
        List<RepairEntity> repairs = repairRepository.findAllByMonthAndYear(date.getMonthValue(), date.getYear());

        Map<String, MonthlyRepairComparisonDTO> currentMonthData = repairs.stream()
                .flatMap(repair -> repairDetailsRepository.findAllByRepairId(repair.getId()).stream())
                .collect(Collectors.groupingBy(
                        RepairDetailsEntity::getRepairType,
                        Collectors.collectingAndThen(Collectors.toList(), repairDetailsList -> {
                            MonthlyRepairComparisonDTO dto = new MonthlyRepairComparisonDTO();
                            dto.setRepairType(repairDetailsList.get(0).getRepairType());

                            int repairCount = repairDetailsList.size();
                            double repairAmount = repairDetailsList.stream().mapToDouble(rd -> rd.getRepairAmount().doubleValue()).sum();

                            dto.setRepairsCountPerMonth3(repairCount);
                            dto.setRepairsAmountPerMonth3(repairAmount);

                            if (previousMonthData != null && previousMonthData.containsKey(dto.getRepairType())) {
                                MonthlyRepairComparisonDTO prevMonthDto = previousMonthData.get(dto.getRepairType());

                                dto.setRepairsCountPerMonth2(prevMonthDto.getRepairsCountPerMonth3());
                                dto.setRepairsAmountPerMonth2(prevMonthDto.getRepairsAmountPerMonth3());

                                if (prevMonthDto.getRepairsCountPerMonth3() != 0) {
                                    dto.setVariationPerMonth2((repairCount - prevMonthDto.getRepairsCountPerMonth3()) / (double) prevMonthDto.getRepairsCountPerMonth3() * 100);
                                } else {
                                    dto.setVariationPerMonth2(repairCount * 100);
                                }

                                if (prevMonthDto.getRepairsAmountPerMonth3() != 0) {
                                    dto.setVariationAmountPerMonth2((repairAmount - prevMonthDto.getRepairsAmountPerMonth3()) / prevMonthDto.getRepairsAmountPerMonth3() * 100);
                                } else {
                                    dto.setVariationAmountPerMonth2(repairAmount * 100);
                                }
                            }

                            return dto;
                        })
                ));

        return currentMonthData;
    }
}