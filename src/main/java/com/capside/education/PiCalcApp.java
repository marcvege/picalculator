package com.capside.education;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PiCalcApp {

    @Bean
    PiCalculator piCalculatorBean() {
        return new PiCalculator();
    }

    
    public static void main(String[] args) {
        SpringApplication.run(PiCalcApp.class, args);
    }
}
