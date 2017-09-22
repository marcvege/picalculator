package com.capside.education;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author ciberado
 */
@RestController
public class PiCtrl {

    private final PiCalculator calculator;

    @Autowired
    public PiCtrl(PiCalculator calculator) {
        this.calculator = calculator;
    }

    @GetMapping(value="/pi", 
                produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
    PiApproximation calculatePiAPI(@RequestParam(defaultValue = "500") int iterations) {
        BigDecimal result = calculator.calculatePI(iterations);
        PiApproximation pi = new PiApproximation(result);
        return pi;
    }
    
    @GetMapping(value={"/", "index.html"},  produces = {MediaType.TEXT_HTML_VALUE})
    ModelAndView showForm() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }
    
    @GetMapping(value={"/pi"}, 
                produces = {MediaType.TEXT_HTML_VALUE})
    ModelAndView calculatePiHTML(@RequestParam(defaultValue = "500") int iterations) {
        BigDecimal result = calculator.calculatePI(iterations);
        ModelAndView mav = new ModelAndView("result");
        mav.addObject("pi", result);
        return mav;
    }
    
}
