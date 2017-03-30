package com.capside.education;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 *
 *
 * @author
 * http://www.codeproject.com/Tips/257031/Implementing-SqrtRoot-in-BigDecimal
 * @author
 * http://stackoverflow.com/questions/9793157/implementing-an-algorithm-to-compute-pi
 */
@ManagedResource(objectName = "bean:name=PiCalculator",
        description = "Pi calculator with a memory bug")
@Log
public class PiCalculator {

    /**
     * If this number if iterations is specified a bug in the code will consume
     * all the available memory.
     */
    private static final int BUGGY_TIME_ITERATIONS = 6667;
    /**
     * Number of 1MB blocks created last time the memory bug was generated.
     */
    private int lastLengthOfTheString = 0;

    private void buggyTimeStackOverflow() {
        lastLengthOfTheString = 0;
        String text = "No por mucho madrugar amanece más temprano.";
        while (true) {
            text = text + text;
            lastLengthOfTheString = text.length();
            log.log(Level.INFO, "I have a String of {0} chars!", lastLengthOfTheString);
        }
    }

    private void buggyTime100CPU() {
        for (int i=1; i < 100; i++) {
            calculatePI(10000);
        }
    }

    @ManagedAttribute(description = "Length of the generated string", currencyTimeLimit = 15)
    public int getLastLengthOfTheString() {
        return lastLengthOfTheString;
    }

    public BigDecimal calculatePI(int iterations) {
        BigInteger firstFactorial;
        BigInteger secondFactorial;
        BigInteger firstMultiplication;
        BigInteger firstExponent;
        BigInteger secondExponent;
        int firstNumber = 1103;
        BigInteger firstAddition;
        BigDecimal currentPi = BigDecimal.ZERO;
        BigDecimal pi = BigDecimal.ONE;
        BigDecimal one = BigDecimal.ONE;
        BigDecimal secondNumber = new BigDecimal("2.0");
        BigDecimal thirdNumber = bigSqrt(secondNumber);
        int fourthNumber = 9801;
        BigDecimal prefix = BigDecimal.ONE;

        for (int i = 0; i < iterations; i++) {
            if (iterations == BUGGY_TIME_ITERATIONS && i == 1000) {
                buggyTime100CPU();
            }
            firstFactorial = factorial(4 * i);
            secondFactorial = factorial(i);
            firstMultiplication = BigInteger.valueOf(26390 * i);
            firstExponent = exponent(secondFactorial, 4);
            secondExponent = exponent(BigInteger.valueOf(396), 4 * i);
            firstAddition = BigInteger.valueOf(firstNumber).add(firstMultiplication);
            currentPi = currentPi.add(new BigDecimal(firstFactorial.multiply(firstAddition)).divide(new BigDecimal(firstExponent.multiply(secondExponent)), new MathContext(1000)));
        }

        prefix = secondNumber.multiply(thirdNumber);
        prefix = prefix.divide(new BigDecimal(fourthNumber), new MathContext(1000));

        currentPi = currentPi.multiply(prefix, new MathContext(1000));

        pi = one.divide(currentPi, new MathContext(1000));
        return pi;
    }

    private BigInteger factorial(int a) {

        BigInteger result = new BigInteger("1");
        BigInteger smallResult = new BigInteger("1");
        long x = a;
        if (x == 1) {
            return smallResult;
        }
        while (x > 1) {
            result = result.multiply(BigInteger.valueOf(x));

            x--;
        }
        return result;
    }

    private BigInteger exponent(BigInteger a, int b) {
        BigInteger answer = new BigInteger("1");

        for (int i = 0; i < b; i++) {
            answer = answer.multiply(a);
        }

        return answer;
    }

    private static final BigDecimal SQRT_DIG = new BigDecimal(150);
    private static final BigDecimal SQRT_PRE = new BigDecimal(10).pow(SQRT_DIG.intValue());

    private BigDecimal sqrtNewtonRaphson(BigDecimal c, BigDecimal xn, BigDecimal precision) {
        BigDecimal fx = xn.pow(2).add(c.negate());
        BigDecimal fpx = xn.multiply(new BigDecimal(2));
        BigDecimal xn1 = fx.divide(fpx, 2 * SQRT_DIG.intValue(), RoundingMode.HALF_DOWN);
        xn1 = xn.add(xn1.negate());
        BigDecimal currentSquare = xn1.pow(2);
        BigDecimal currentPrecision = currentSquare.subtract(c);
        currentPrecision = currentPrecision.abs();
        if (currentPrecision.compareTo(precision) <= -1) {
            return xn1;
        }
        return sqrtNewtonRaphson(c, xn1, precision);
    }

    public BigDecimal bigSqrt(BigDecimal c) {
        return sqrtNewtonRaphson(c, new BigDecimal(1), new BigDecimal(1).divide(SQRT_PRE));
    }

}
