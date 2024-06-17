package com.autofix.msrepairs.clients;

import com.autofix.msrepairs.configurations.FeignClientConfig;
import com.autofix.msrepairs.requests.VehicleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ms-vehicles", path = "/vehicles", configuration = FeignClientConfig.class)
public interface VehicleFeignClient {
    @GetMapping("/{id}")
    VehicleDTO getVehicleById(@PathVariable("id") Long id);
}

