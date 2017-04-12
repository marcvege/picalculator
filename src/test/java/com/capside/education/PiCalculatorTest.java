/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.capside.education;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Javi
 */
public class PiCalculatorTest {
    private static final BigDecimal PI = new BigDecimal(      
       "3.14159265358979323846264338327950288419716939937" + 
       "5105820974944592307816406286208998628034825342117" + 
       "06798214808651328230664709384");
    private static final MathContext MC = new MathContext(4,RoundingMode.HALF_UP);
    
    public PiCalculatorTest() {
    }

    @Test
    public void checkPrecission20DecimalsAfter5Iterations100Times() {
        BigDecimal precission = new BigDecimal("1E-20");
        PiCalculator calc = new PiCalculator();

        for (int i=0; i < 100; i++) {
            BigDecimal pi = calc.calculatePI(5);
            BigDecimal diff = pi.subtract(PI).abs(MC);
            assertTrue("Precission greater than 20 decimals for 5 iterations.", 
                       diff.compareTo(precission) < 0);
        }
    }
    
}
