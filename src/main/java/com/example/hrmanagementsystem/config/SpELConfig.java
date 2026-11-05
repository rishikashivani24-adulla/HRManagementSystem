package com.example.hrmanagementsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpELConfig {

    @Value("#{2 + 3}")
    private int result;

    public int getResult() {
        return result;
    }
}