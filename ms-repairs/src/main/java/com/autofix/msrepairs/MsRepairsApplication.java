package com.autofix.msrepairs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRepairsApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsRepairsApplication.class, args);
    }

}