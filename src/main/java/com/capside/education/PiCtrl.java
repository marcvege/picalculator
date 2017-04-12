package com.capside.education;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ciberado
 */
@RestController
public class PiCtrl {

    private PiCalculator calculator;

    @Autowired
    public PiCtrl(PiCalculator calculator) {
        this.calculator = calculator;
    }

    @RequestMapping(value = "/pi", method = RequestMethod.GET)
    PiApproximation calculatePi(@RequestParam(defaultValue = "500") int iterations) {
        BigDecimal result = calculator.calculatePI(iterations);
        PiApproximation pi = new PiApproximation(result);
        return pi;
    }
}
