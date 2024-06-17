package com.autofix.msreports;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsReportsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsReportsApplication.class, args);
    }

}
