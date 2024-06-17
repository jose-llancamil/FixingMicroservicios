package com.autofix.msvehicles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsVehiclesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsVehiclesApplication.class, args);
	}

}