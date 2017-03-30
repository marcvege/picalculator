package com.capside.education;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PicalculatorwebserviceApplication {

    @Autowired
    private PiCalculator calculator;
    
    @Bean
    PiCalculator piCalculatorBean() {
        return new PiCalculator();
    }
    
    @RequestMapping(value="/pi", method = RequestMethod.GET)
    PiApproximation calculatePi(@RequestParam(defaultValue = "500") int iterations) {
        BigDecimal result = calculator.calculatePI(iterations);
        PiApproximation pi = new PiApproximation(result);
        return pi;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(PicalculatorwebserviceApplication.class, args);
    }
}
