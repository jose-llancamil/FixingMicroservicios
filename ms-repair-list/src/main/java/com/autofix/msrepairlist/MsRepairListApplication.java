package com.autofix.msrepairlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRepairListApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRepairListApplication.class, args);
	}

}