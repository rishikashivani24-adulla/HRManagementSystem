package com.example.hrmanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HrManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrManagementSystemApplication.class, args);
    }
}